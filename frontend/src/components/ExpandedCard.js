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

const expandedCard = ({ carName, price, location, size, fuelType, transmission, description, availability }) => {
    return (
        <Card className="">
            <CardHeader>
                <CardTitle>{carName}</CardTitle>
                <CardDescription>{description}</CardDescription>
            </CardHeader>
            <CardContent>
                <Image className="rounded" src={carImage} alt={carName} width={400} height={200} />
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
        </Card>
    )
}

export default expandedCard;
