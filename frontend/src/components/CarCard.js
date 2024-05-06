import * as React from "react"
import Image from "next/image"
import carImage from "@/img/cars/BMW-M3.jpg"

import {
    Card,
    CardContent,
    CardDescription,
    CardFooter,
    CardHeader,
    CardTitle,
} from "@/components/ui/card"

const CarCard = ({ carName, price, location, size, fuelType, transmission, description, availability }) => {
    return (
        <Card className="flex flex-row">
            <Image className="rounded" src={carImage} alt={carName} width={400} height={200} />
            <div className="flex flex-col">
                <CardHeader>
                    <CardTitle>{carName}</CardTitle>
                    <CardDescription>{description}</CardDescription>
                </CardHeader>
                <CardContent>
                    {availability ? "available" : "unavailable"}
                    <p>Price: {price} NOK/day</p>
                    <p>Location: {location}</p>
                </CardContent>
                <CardFooter className="flex flex-row">
                    <div>
                        <div>Size: {size}</div>
                        <div>Fuel type: {fuelType}</div>
                        <div>Transmission: {transmission}</div>
                    </div>
                </CardFooter>
            </div>
        </Card>
    )
}

export default CarCard;
