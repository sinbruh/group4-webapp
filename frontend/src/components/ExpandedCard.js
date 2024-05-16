import * as React from "react"
import Image from "next/image"
import carImage from "@/img/cars/BMW-M3.jpg"
import carIcon from "@/img/icons/car.svg";
import checkIcon from "@/img/icons/checkmark.svg";
import closeIcon from "@/img/icons/close.svg";
import cogIcon from "@/img/icons/cog.svg";
import peopleIcon from "@/img/icons/people.svg";
import PriceSelector from "@/components/PriceSelector"

import {
    Card,
    CardContent,
    CardDescription,
    CardFooter,
    CardHeader,
    CardTitle,
} from "@/components/ui/card"

const expandedCard = ({ carInfo }) => {
    const carImage = carInfo.carImageInput ? `/carsLowResWEBP/${carInfo.carImageInput}` : carDefaultImage;

    const handleClick = () => {
        console.log("Book now button clicked")
    }

    return (
        <Card id="expandedCard" className="h-full">
            {(carInfo != null )? (
                <div>
                    <CardHeader>
                        <CardTitle>{carInfo.carName}</CardTitle>
                        <CardDescription>{carInfo.description}</CardDescription>
                    </CardHeader>
                    <CardContent>
                        <Image className="rounded" src={carImage} alt={carInfo.carName} width={400} height={200} />
                        {carInfo.availability ?
                            <div className="text-[#326D0D] font-bold flex gap-2 items-center"><Image src={checkIcon} width={32} height={32} /> Available </div> :
                            <div className="text-red-500 font-bold flex gap-2 items-center"><Image src={closeIcon} width={32} height={32} /> Unavailable </div>}
                        <p>Price: {carInfo.price} NOK/day</p>
                        <p>Location: {carInfo.location}</p>
                    </CardContent>
                    <CardFooter className="flex flex-row">
                        <div className="flex gap-2">
                            <div className="flex items-center"><Image src={peopleIcon} alt="people" width={32} height={32} /> {carInfo.size}</div>
                            <div className="flex items-center"><Image src={carIcon} alt="fuel type" widht={32} height={32} /> {carInfo.fuelType}</div>
                            <div className="flex items-center"><Image src={cogIcon} alt="transmission" width={32} height={32} /> {carInfo.transmission}</div>
                        </div>
                    </CardFooter>
                    <CardFooter>
                        <div className="flex gap-5">
                        <button onClick={handleClick()} className="bg-[#326D0D] text-white font-bold py-2 px-4 rounded">Book now</button>
                        <PriceSelector providers={carInfo.providers} />
                        </div>
                    </CardFooter>
                </div>
            ) : (
                <CardHeader>
                    <CardTitle>Choose a car to see more details</CardTitle>
                </CardHeader>
            )}
        </Card>
    )
}

export default expandedCard;
