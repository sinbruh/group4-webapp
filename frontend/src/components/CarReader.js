import React, { useEffect, useState } from "react";
import CarCard from "@/components/CarCard";
import ExpandedCard from "@/components/ExpandedCard";
import { asyncApiRequest } from "@/tools/request";
import { getCookie } from "@/tools/cookies";
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
  const [user, setUser] = useState(null);
  const Email = getCookie("current_email");
  const [addFavorite] = useFavoriteStore((state) => [state.addFavorite]);

  const updateJsonFile = async () => {
    try {
      //check data
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

      //update CarCards
      setCars(data);
    } catch (error) {
      console.error("Error updating JSON file:", error);
    }
  };


    const UserInfo = async () => {
      try {
          let data = await asyncApiRequest("GET", "/api/users/" + Email, {
              headers: {
                  Authorization: "Bearer " + getCookie("jwt"),
              },
          });

          if (data) {
              setUser(data);
              data.favorites.forEach((favorite) => {
                  addFavorite(favorite.id);
              });

          }
      } catch (error) {
          console.error("Error fetching user information", error);
      }
  };

  useEffect(() => {
    UserInfo();
  }, []);

  useEffect(() => {
    updateJsonFile();
  }, [location, dates, price]);

  return (
    <div>
      <div id="cards">
        {cars
          .flatMap((car) =>
            car.configurations[0].providers.map((provider) => ({
              ...car,
              provider,
            }))
          )
          .filter(({ provider }) => {
            if (
              typeof price === "undefined" ||
              typeof location === "undefined"
            ) {
              return true;
            }

            const lowestPrice = provider.price;
            const fromPriceNumber = Number(price.min);
            const toPriceNumber = Number(price.max);
            const carLocationLowercase = provider.location.toLowerCase();
            const locationLowercase = location.toLowerCase();

            if (!isNaN(fromPriceNumber) && !isNaN(toPriceNumber)) {
              const isFromPriceLower = fromPriceNumber <= lowestPrice;
              const isToPriceHigher =
                toPriceNumber >= lowestPrice || toPriceNumber === 0;
              const isLocationMatch =
                carLocationLowercase === locationLowercase || location === "";
              const isFavorite = favoriteFilter ? user.favorites && user.favorites.some(favorite => favorite.id === provider.id) : true; // Add this line
              if (isFromPriceLower && isToPriceHigher && isLocationMatch && isFavorite) { // Update this line
                return lowestPrice;
              }
            }
            return false;
          })
          .map(({ configurations, provider, make, model }) => {
            const carImageName = configurations[0].img || "default.jpg";

            const allRentals = configurations.flatMap((configuration) =>
              configuration.providers.flatMap((provider) => provider.rentals)
            );

            const providerRentals = provider.rentals || [];

            const isAvailable = providerRentals
              ? providerRentals.every((rental) => {
                  const rentalStartDate = new Date(rental.startDate);
                  const rentalEndDate = new Date(rental.endDate);
                  return rentalStartDate > toDate || rentalEndDate < fromDate;
                })
              : false;

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
              <CarCard
                key={provider.id}
                carInfo={carInfo}
                setExpandedCarInfo={setExpandedCarInfo}
              />
            );
          })}
      </div>

      {expandedCar && <ExpandedCard carInfo={carInfo} />}
    </div>
  );
}
