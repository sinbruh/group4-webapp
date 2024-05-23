import { create } from "zustand";
import { asyncApiRequest } from "@/tools/request";
import CarCard from "@/components/CarCard";


export const useCarData = create((set) => ({
    cars: [],
    addCar: (car) => set((state) => {
        // Check if the car already exists in the array
        const carExists = state.cars.some(existingCar => existingCar.id === car.id);
        // If the car does not exist, add it to the array
        if (!carExists) {
            return { cars: [...state.cars, car] };
        }
        // If the car exists, return the existing state
        return state;
    }),
    removeCar: (car) =>
        set((state) => ({
            cars: state.cars.filter((item) => item.id !== car.id),
        })),
    bookCar: (carId) =>
        set((state) => ({
            cars: state.cars.map((car) => {
                if (car.provider.id === carId) {
                    return { ...car, isBooked: true };
                }
                return car;
            }),
        })),
}));


export const fetchData = async () => {

    //remove all cars first
    useCarData.getState().cars = [];


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

            data.forEach((car) => useCarData.getState().addCar(car));
        }

    } catch (error) {
        console.error("Error fetching car data:", error);
    }
};

// export function flattenCars(cars) {
//     return cars.flatMap((car) =>
//         car.configurations.flatMap((configuration) =>
//             configuration.providers.map((provider) => ({
//                 ...car,
//                 configuration,
//                 provider,
//             }))
//         )
//     );
//
// }

export function flattenCars(cars) {
    return cars.flatMap((car) => {
        // Check if the car object has a 'configurations' property
        if (car.configurations) {
            return car.configurations.flatMap((configuration) =>
                configuration.providers.map((provider) => ({
                    id: car.id,
                    make: car.make,
                    model: car.model,
                    year: car.year,
                    configuration: {
                        id: configuration.id,
                        img: configuration.img,
                        numberOfSeats: configuration.numberOfSeats,
                        fuelType: configuration.fuelType,
                        transmissionType: configuration.transmissionType,
                        extraFeatures: configuration.extraFeautres,
                    },
                    provider: {
                        id: provider.id,
                        name: provider.name,
                        price: provider.price,
                        location: provider.location,
                        rentals: provider.rentals,
                    },
                }))
            );
        } else {
            // Return an empty array if 'configurations' property is not found
            return [];
        }
    });
}


// export function filterCars(cars, location, price, favoriteFilter, favorites) {
//     return cars.filter(({ car }) => {
//         const fromPriceNumber = Number(price?.min) || 0;
//         const toPriceNumber = Number(price?.max) || 1000;
//         const isPriceInRange = car.configuration.provider.price ? fromPriceNumber <= lowestPrice && (toPriceNumber >= lowestPrice || toPriceNumber === 0) : true;
//         const isLocationMatch = location ? car.configuration.provider.toLowerCase() === location.toLowerCase() : true;
//         const isFavorite = favoriteFilter ? favorites.includes(car.configuration.provider.id) : true;
//
//         return isPriceInRange && isLocationMatch && isFavorite;
//     })
// }
export function filterCars(cars, location, price, favoriteFilter, favorites) {
    return cars.filter((car) => {
        const fromPriceNumber = Number(price?.min) || 0;
        const toPriceNumber = Number(price?.max) || 1000;
        const provider = car.provider || {};
        const lowestPrice = provider.price || 0;

        const isPriceInRange = fromPriceNumber <= lowestPrice && (toPriceNumber >= lowestPrice || toPriceNumber === 0);
        const isLocationMatch = location ? provider.location?.toLowerCase() === location.toLowerCase() : true;
        const isFavorite = favoriteFilter ? favorites.includes(provider.id) : true;

        return isPriceInRange && isLocationMatch && isFavorite;
    });
}



export function createCarCards(cars, setExpandedCarInfo, dates) {


    return cars.map((car) => {
        const isAvailable = car.provider.rentals.every((rental) => {
            const rentalStartDate = new Date(rental.startDate);
            const rentalEndDate = new Date(rental.endDate);
            return rentalStartDate > dates[0] || rentalEndDate < dates[1];
        });
        const carInfo = {
            configId: car.configuration.id,
            carImageInput: car.configuration.img || "default.jpg",
            carName: `${car.make} ${car.model}`,
            price: car.provider.price,
            location: car.provider.location || 'undefined',
            size: car.configuration.numberOfSeats,
            fuelType: car.configuration.fuelType,
            transmission: car.configuration.tranmissionType,
            availability: isAvailable,
            provider: car.provider,
            configuration: car.configuration,
            rentals: car.provider.rentals,
        };
        return <CarCard key={car.provider.id} carInfo={carInfo} setExpandedCarInfo={setExpandedCarInfo} />;
    });
}
