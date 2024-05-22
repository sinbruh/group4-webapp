import React, { useEffect, useState } from "react";
import CarCard from "@/components/CarCard";
import ExpandedCard from "@/components/ExpandedCard";
import { asyncApiRequest } from "@/tools/request";
import { useFavoriteStore } from "@/tools/favorite";

export default function CarReader({
  location,
  dates,
  price,
  setExpandedCarInfo,
  favoriteFilter
}) {
  const [cars, setCars] = useState([]);
  const [expandedCar, setExpandedCar] = useState(null);
  const fromDate = dates ? dates.from : null;
  const toDate = dates ? dates.to : null;
  const [favorites, addFavorite] = useFavoriteStore((state) => [state.favorites, state.addFavorite]);
  const [isLoading, setIsLoading] = useState(true);
  const [showCarCard, setShowCarCard] = useState(false);

  const updateJsonFile = async () => {
    setIsLoading(true);
    try {
      let data = await asyncApiRequest("GET", "/api/cars");
  
      if (data) {
        // Add img property to each car configuration
        data = data.map((item) => {
          item.configurations = item.configurations.map((config) => {
            config.img = `${item.make.replace(/ /g, "-")}-${item.model.replace(
              / /g,
              "-"
            )}.webp`;
            return config;
          });
          return item;
        });
      }
  
      
      setCars(data);
      setIsLoading(false);
    } catch (error) {
      console.error("Error updating JSON file:", error);
    }
  };
  
  useEffect(() => {
    updateJsonFile();
  }, [location, dates, price]);

  useEffect(() => {
    const timer = setTimeout(() => {
      setShowCarCard(true);
    }, 3000); // 3000ms delay
  
    return () => clearTimeout(timer); // Clean up on component unmount
  }, []);

  return (
    <div>
      {isLoading ? (
        <div>Loading...</div>
      ) : (
      <div id="cards">
        {cars
          .flatMap((car) =>
            car.configurations[0].providers.map((provider) => ({
              ...car,
              provider,
            }))
          )
          .filter(({ provider }) => {
            const { price: lowestPrice = 0, location: carLocation = '' } = provider;
            const fromPriceNumber = Number(price?.min) || 0;
            const toPriceNumber = Number(price?.max) || Infinity;
            const isPriceInRange = price ? fromPriceNumber <= lowestPrice && (toPriceNumber >= lowestPrice || toPriceNumber === 0) : true;
            const isLocationMatch = location ? carLocation.toLowerCase() === location.toLowerCase() : true;
            const isFavorite = favoriteFilter ? favorites.includes(provider.id) : true;
          
            return isPriceInRange && isLocationMatch && isFavorite;
          })
          .map(({ configurations, provider, make, model }) => {
            if (!configurations || configurations.length === 0) {
              throw new Error('No configurations found for this car');
            }
          
            const firstConfig = configurations[0];
            const carImageName = firstConfig.img || "default.jpg";
            const providerRentals = provider.rentals || [];
          
            const isAvailable = providerRentals.every((rental) => {
            const rentalStartDate = new Date(rental.startDate);
            const rentalEndDate = new Date(rental.endDate);
            return rentalStartDate > fromDate || rentalEndDate < toDate;
            });

            const carInfo = {
              configId: configurations[0].id,
              carImageInput: carImageName,
              carName: `${make} ${model}`,
              price: provider.price,
              location: provider.location|| 'undefined',
              size: configurations[0].numberOfSeats,
              fuelType: configurations[0].fuelType,
              transmission: configurations[0].tranmissionType,
              availability: isAvailable,
              provider: [provider],
              configurations: configurations,
              rentals: providerRentals,
            };

            return (
              showCarCard && (
              <CarCard
                key={provider.id}
                carInfo={carInfo}
                setExpandedCarInfo={setExpandedCarInfo}
              />
              )
            );
          })}
      </div>
      )}
      {expandedCar && <ExpandedCard carInfo={carInfo} />}
    </div>
  );
}
