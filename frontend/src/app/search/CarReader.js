import React, { useEffect, useState } from 'react';
import CarCard from "@/components/CarCard";
import ExpandedCard from "@/components/ExpandedCard";

const CarReader = () => {
    const [cars, setCars] = useState([]);
    const [expandedCar, setExpandedCar] = useState(null);
    //const [fromPrice, setFromPrice] = useState(0);
    //const [toPrice, setToPrice] = useState(Infinity);

    const updateJsonFile = async () => {
        try {
            //clean old data
            localStorage.removeItem('cars');

            //get data
            const response = await fetch('http://localhost:8080/api/cars');
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            const data = await response.json();

            

            //update CarCards
            setCars(data);


            console.log('Cars:', data);
        } catch (error) {
            console.error('Error updating JSON file:', error);
        }
    };
        
        useEffect(() => {
            updateJsonFile().catch(console.error);
            console.log('CarReader component has mounted');
            console.log('Cars:', cars);
        }, []);

    const handleCardClick = (car) => {
        setExpandedCar(car);
    };

    return (
        <div>
             

            <div id="cards">
                {cars
                    // cars.filter(car => {
                   // const prices = car.configurations[0].providers.map(provider => provider.price);
                   // const lowestPrice = Math.min(...prices);
                   // return lowestPrice >= fromPrice && lowestPrice <= toPrice;
                   // })
                .map(car => (
                    <CarCard 
                        key={car.id} 
                        carName={`${car.make} ${car.model}`} 
                        price={Math.min(...car.configurations[0].providers.map(provider => provider.price))} 
                        location={car.configurations[0].location} 
                        size={car.configurations[0].numberOfSeats} 
                        fuelType={car.configurations[0].fuelType} 
                        transmission={car.configurations[0].tranmissionType} 
                        description={car.description} 
                        availability={car.availability} 
                        onClick={() => handleCardClick(car)} 
                    />
                ))
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

export default CarReader;