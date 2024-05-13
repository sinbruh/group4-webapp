import * as React from "react"
import Image from "next/image"
import carImage from "@/img/cars/BMW-M3.jpg"
import carIcon from "@/img/icons/car.svg";
import checkIcon from "@/img/icons/checkmark.svg";
import closeIcon from "@/img/icons/close.svg";
import cogIcon from "@/img/icons/cog.svg";
import peopleIcon from "@/img/icons/people.svg";

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
        <Card className="h-4/5">
            <CardHeader>
                <CardTitle>{carName}</CardTitle>
                <CardDescription>{description}</CardDescription>
            </CardHeader>
            <CardContent>
                <Image className="rounded" src={carImage} alt={carName} width={400} height={200} />
                {availability ?
                    <div className="text-[#326D0D] font-bold flex gap-2 items-center"><Image src={checkIcon} width={32} height={32} /> Available </div> :
                    <div className="text-red-500 font-bold flex gap-2 items-center"><Image src={closeIcon} width={32} height={32} /> Unavailable </div>}
                <p>Price: {price} NOK/day</p>
                <p>Location: {location}</p>
            </CardContent>
            <CardFooter className="flex flex-row">
                <div className="flex gap-2">
                    <div className="flex items-center"><Image src={peopleIcon} alt="people" width={32} height={32} /> {size}</div>
                    <div className="flex items-center"><Image src={carIcon} alt="fuel type" widht={32} height={32} /> {fuelType}</div>
                    <div className="flex items-center"><Image src={cogIcon} alt="transmission" width={32} height={32} /> {transmission}</div>
                </div>
            </CardFooter>
        </Card>
    )
}

export default expandedCard;
