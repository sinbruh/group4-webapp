import * as React from "react"
import Image from "next/image"
import carDefaultImage from "@/img/cars/BMW-M3.jpg"
import carIcon from "@/img/icons/car.svg";
import checkIcon from "@/img/icons/checkmark.svg";
import closeIcon from "@/img/icons/close.svg";
import cogIcon from "@/img/icons/cog.svg";
import peopleIcon from "@/img/icons/people.svg";
import FavoriteButton from "@/components/FavoriteButton"

import {
    Card,
    CardContent,
    CardDescription,
    CardFooter,
    CardHeader,
    CardTitle,
} from "@/components/ui/card"
import { document } from "postcss";

const CarCard = ({ carInfo, setExpandedCarInfo }) => {
    const carImage = carInfo.carImageInput ? `/carsLowRes/${carImageInput}` : carDefaultImage;
    console.log(carImage);
    console.log(carInfo.carImageInput);

    function handleOnClick(){
        console.log("Car card clicked");

        setExpandedCarInfo(carInfo);
    }

    return (
        <div onClick={handleOnClick} className="hover:cursor-pointer">
        <Card className="flex flex-row items-start justify-between mb-4">

            <div className="flex flex-row">
                <Image className="rounded" src={carImage} alt={carInfo.carName} width={400} height={200} />

                <div className="flex flex-col">
                    <CardHeader>
                        <CardTitle>
                            <div className="flex justify-between">
                                <p>
                                    {carInfo.carName}
                                </p>
                            </div>
                        </CardTitle>
                        <CardDescription>{carInfo.description}</CardDescription>
                    </CardHeader>
                    <CardContent>
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
                </div>
            </div>
            <div className="m-4">
                <FavoriteButton configID={carInfo.configID}/>
            </div>

        </Card>
        </div>
    )
}

export default CarCard;
