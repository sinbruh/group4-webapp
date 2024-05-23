import * as React from "react"
import Image from "next/image"
import carDefaultImage from "@/img/cars/BMW-M3.jpg"
import carIcon from "@/img/icons/car.svg";
import checkIcon from "@/img/icons/checkmark.svg";
import closeIcon from "@/img/icons/close.svg";
import cogIcon from "@/img/icons/cog.svg";
import peopleIcon from "@/img/icons/people.svg";
import FavoriteButton from "@/components/FavoriteButton";
import { isLoggedIn } from "@/tools/authentication.js";
import { useMediaQuery } from 'react-responsive';

import {
    Card,
    CardContent,
    CardDescription,
    CardFooter,
    CardHeader,
    CardTitle,
} from "@/components/ui/card"

import {
    Dialog,
    DialogContent,
} from "@/components/ui/dialog"

import ExpandedCard from "@/components/ExpandedCard";

const CarCard = ({ carInfo, setExpandedCarInfo }) => {
    const [expandedIsOpen, setExpandedIsOpen] = React.useState(false);
    const carImage = carInfo.carImageInput ? `/carsLowResWEBP/${carInfo.carImageInput}` : carDefaultImage;
    const isDesktop = useMediaQuery({ query: '(min-width: 1450px)' });

    function handleOnClick() {
        if (isDesktop) {
            setExpandedCarInfo(carInfo);
        } else {
            setExpandedIsOpen(true);
        }
    }

    return (
        <div>
            <Dialog open={expandedIsOpen} onOpenChange={setExpandedIsOpen}>
                <DialogContent>
                    <ExpandedCard carInfo={carInfo} />
                </DialogContent>
            </Dialog>

            <div onClick={handleOnClick} className="hover:cursor-pointer">
                <Card className="flex flex-row items-start justify-between mb-4">

                    <div className="flex flex-row">
                        {isDesktop &&
                            <Image className="rounded" src={carImage} alt={carInfo.carName} width={400} height={200} />
                        }
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
                            {!isDesktop &&
                                <Image className="rounded" src={carImage} alt={carInfo.carName} width={400} height={200} />
                            }
                            <CardContent>
                                {carInfo.availability ?
                                    <div className="text-[#326D0D] font-bold flex gap-2 items-center"><Image src={checkIcon} alt={"Available"} width={32} height={32} /> Available </div> :
                                    <div className="text-red-500 font-bold flex gap-2 items-center"><Image src={closeIcon} alt={"UnAvailable"}width={32} height={32} /> Unavailable </div>}
                                <p>Price: {carInfo.price} NOK/day</p>
                                <p>Location: {carInfo.location}</p>
                            </CardContent>
                            <CardFooter className="flex flex-row">
                                <div className="flex flex-wrap gap-2">
                                    <div className="flex items-center"><Image src={peopleIcon} alt="people" width={32} height={32} /> {carInfo.size}</div>
                                    <div className="flex items-center"><Image src={carIcon} alt="fuel type" widht={32} height={32} /> {carInfo.fuelType}</div>
                                    <div className="flex items-center"><Image src={cogIcon} alt="transmission" width={32} height={32} /> {carInfo.configuration.transmission}</div>
                                </div>
                            </CardFooter>
                        </div>
                    </div>
                    <div className="m-4">
                        {isLoggedIn() && <FavoriteButton carInfo={carInfo} />}
                    </div>

                </Card>
            </div>
        </div>
    )
}

export default CarCard;
