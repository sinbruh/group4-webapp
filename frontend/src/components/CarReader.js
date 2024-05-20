import React, { useEffect, useState } from 'react';
import CarCard from "@/components/CarCard";
import ExpandedCard from "@/components/ExpandedCard";

export default function CarReader({ location, dates, price, setExpandedCarInfo }) {
    const [cars, setCars] = useState([]);
    const [expandedCar, setExpandedCar] = useState(null);
    const fromDate = dates ? dates.from : null;
    const toDate = dates ? dates.to : null;

    const updateJsonFile = async () => {
        try {


            //check data
            const response = await fetch('http://localhost:8080/api/cars');
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }

            let data = await response.json();

            if (data) {


            // Add img property to each car configuration
            data = data.map(item => {
                item.configurations = item.configurations.map(config => {
                    config.img = `${item.make.replace(/ /g, '-')}-${item.model.replace(/ /g, '-')}.webp`;
                    return config;
                });
                return item;
            });
        }

            //update CarCards
            setCars(data);
        } catch (error) {
            console.error('Error updating JSON file:', error);
        }
    };

        useEffect(() => {


            updateJsonFile().catch(console.error);

        }, [location, dates, price]);

    const handleCardClick = (car) => {
        setExpandedCar(car);
    };

    return (
        <div>
            <div id="cards">
                {cars
                    .flatMap(car => car.configurations[0].providers.map(provider => ({...car, provider})))
                    .filter(({provider}) => {
                        if (typeof price === 'undefined' || typeof location === 'undefined') {
                            return true;
                        }

                        const lowestPrice = provider.price;
                        const fromPriceNumber = Number(price.min);
                        const toPriceNumber = Number(price.max);
                        const carLocationLowercase = provider.location.toLowerCase();
                        const locationLowercase = location.toLowerCase();
                        const carAvailability = provider.available;



                        if (!isNaN(fromPriceNumber) && !isNaN(toPriceNumber)) {
                            const isFromPriceLower = fromPriceNumber <= lowestPrice;
                            const isToPriceHigher = toPriceNumber >= lowestPrice || toPriceNumber === 0;
                            const isLocationMatch = carLocationLowercase  === locationLowercase || location === '';
                            if (isFromPriceLower && isToPriceHigher && isLocationMatch) {
                                return lowestPrice;
                            }
                        }
                        return false;
                    })
                    .map(({configurations, provider, make, model}) => {
                        const carImageName = configurations[0].img || 'default.jpg';


                        const rentals = configurations[0].providers[0].rentals;
                        const isAvailable = rentals.every(rental => {
                            const rentalStartDate = new Date(rental.startDate);
                            const rentalEndDate = new Date(rental.endDate);
                            return rentalStartDate > toDate || rentalEndDate < fromDate;
                        });

                        const carInfo = {
                            configId : configurations[0].id,
                            carImageInput : carImageName,
                            carName : `${make} ${model}`,
                            price : provider.price,
                            location : provider.location,
                            size : configurations[0].numberOfSeats,
                            fuelType : configurations[0].fuelType,
                            transmission : configurations[0].tranmissionType,
                            availability : isAvailable,
                            provider : [provider],
                            configurations: configurations
                        }



                        return (
                        <CarCard key={provider.id} carInfo={carInfo} setExpandedCarInfo={setExpandedCarInfo}/>
                    );
                })
            }
            </div>

            {expandedCar &&
            <ExpandedCard carInfo={carInfo}/>
            }
        </div>
    );
};

