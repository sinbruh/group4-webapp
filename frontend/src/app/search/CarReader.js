import React, { useEffect, useState } from 'react';
import CarCard from "@/components/CarCard";
import ExpandedCard from "@/components/ExpandedCard";

export default function CarReader({ location, dates, price }) {
    const [cars, setCars] = useState([]);
    const [expandedCar, setExpandedCar] = useState(null); 

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
                    config.img = `${item.make.replace(/ /g, '-')}-${item.model.replace(/ /g, '-')}.jpg`;
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
                    .filter(car => {
                        const prices = car.configurations[0].providers.map(provider => provider.price);
                        const lowestPrice = Math.min(...prices);
                        const fromPriceNumber = Number(price.min);
                        const toPriceNumber = Number(price.max);
                        const carLocation = car.configurations[0].location;
                        const locationLowercase = location.toLowerCase();
                        const carAvailability = car.configurations[0].available;

                        

                        if (fromPriceNumber && toPriceNumber && !isNaN(fromPriceNumber) && !isNaN(toPriceNumber)) {
                            return lowestPrice >= fromPriceNumber && lowestPrice <= toPriceNumber;
                        } else {
                            return lowestPrice;
                        }
                    })
                    .map(car => {
                        const carImageName = car.configurations[0].img || 'default.jpg';
                        return (
                        <CarCard 
                            key={car.id} 
                            configID={car.configurations[0].id} 
                            carImageInput={carImageName}
                            carName={`${car.make} ${car.model}`} 
                            price={Math.min(...car.configurations[0].providers.map(provider => provider.price))} 
                            location={car.configurations[0].location} 
                            size={car.configurations[0].numberOfSeats} 
                            fuelType={car.configurations[0].fuelType} 
                            transmission={car.configurations[0].tranmissionType} 
                            description={car.description}
                            availability={car.configurations[0] && car.configurations[0].available ? 'Available' : 'Unavailable'}
                            onClick={() => handleCardClick(car)} 
                        />
                    );
                })
            }
            </div>

            {expandedCar && 
            <ExpandedCard 
            carName={`${expandedCard.make} ${expandedCard.model}`} 
            price={Math.min(...expandedCard.configurations[0].providers.map(provider => provider.price))} 
            location={expandedCard.configurations[0].location} 
            size={expandedCard.configurations[0].numberOfSeats} 
            fuelType={expandedCard.configurations[0].fuelType} 
            transmission={expandedCard.configurations[0].tranmissionType} 
            description={expandedCard.description} 
            availability={expandedCard.availability} 
            />
            }
        </div>
    );
};

