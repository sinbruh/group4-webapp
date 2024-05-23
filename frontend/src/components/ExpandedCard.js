import * as React from "react"
import Image from "next/image"
import carDefaultImage from "@/img/cars/BMW-M3.jpg"
import carIcon from "@/img/icons/car.svg";
import checkIcon from "@/img/icons/checkmark.svg";
import closeIcon from "@/img/icons/close.svg";
import cogIcon from "@/img/icons/cog.svg";
import peopleIcon from "@/img/icons/people.svg";
import { asyncApiRequest } from "@/tools/request"
import { UserNotLoggedInAlert } from "@/components/alerts/UserNotLoggedInAlert"
import ConfirmationAlert from "@/components/alerts/ConfirmationAlert"
import { AlreadyBookedAlert } from "@/components/alerts/AlreadyBookedAlert";

import {
    Card,
    CardContent,
    CardDescription,
    CardFooter,
    CardHeader,
    CardTitle,
} from "@/components/ui/card"

import { useStore } from "@/tools/authentication"

const expandedCard = ({ carInfo, dates, showConfirmation}) => {
    const user = useStore((state) => state.user);
    const [isOpen, setIsOpen] = React.useState(false);
    const [alreadyBooked, setAlreadyBooked] = React.useState(false);
    const confirmationDialogRef = React.useRef();

    let carImage;
    if (carInfo && carInfo.carImageInput) {
        carImage = carInfo.carImageInput ? `/carsLowResWEBP/${carInfo.carImageInput}` : carDefaultImage;
    }

    const handleClick = async () => {
        if (carInfo.availability) {
            if (user) {
                const bookingConfirmed = await confirmationDialogRef.current.openDialog();
                if (bookingConfirmed) {
                    const body = {
                        startDate: dates.start,
                        endDate: dates.end,
                    }

                    const response = await asyncApiRequest("POST", "/api/rentals/" + user.email + "/" + carInfo.provider.id, body)
                    if (response) {
                        console.log("Booking successful")
                        console.log("Booking response: ", response)

                        const rentalID = response;

                        sendReceiptRequest(rentalID)

                        showConfirmation();
                    } else {
                        setAlreadyBooked(true);
                    }
                }
            } else {
                setIsOpen(true);
            }
        } else {
            setAlreadyBooked(true);
        }
    }

    function getTotalPrice() {
        const days = Math.floor((dates.end - dates.start) / (1000 * 60 * 60 * 24));
        return days * carInfo.price;
    }

    async function sendReceiptRequest(rentalID) {
        const total = getTotalPrice();
        console.log("Sending receipt request with", user.email, rentalID, total)
        const response = await asyncApiRequest("POST", "/api/receipts/" + user.email + "/" + rentalID, total)
        console.log("Receipt response: ", response)
    }

    return (
        <div className="h-full">
            <ConfirmationAlert ref={confirmationDialogRef} />
            <AlreadyBookedAlert isOpen={alreadyBooked} setIsOpen={setAlreadyBooked} />
            <UserNotLoggedInAlert isOpen={isOpen} setIsOpen={setIsOpen} />
            <Card id="expandedCard" className="h-full flex flex-col">
                {(carInfo != null) ? (
                    <div className="h-full flex flex-col overflow-hidden">
                        <CardHeader>
                            <CardTitle>{carInfo.carName}</CardTitle>
                            <CardDescription>{carInfo.description}</CardDescription>
                        </CardHeader>
                        <CardContent className="flex-grow overflow-y-auto">
                            <Image className="rounded" src={carImage} alt={carInfo.carName} width={400} height={200} />

                            {carInfo.availability ?
                                <div className="text-[#326D0D] font-bold flex gap-2 items-center"><Image src={checkIcon} alt={"Available"} width={32} height={32} /> Available </div> :
                                <div className="text-red-500 font-bold flex gap-2 items-center"><Image src={closeIcon} alt={"unAvailable"} width={32} height={32} /> Unavailable </div>}
                            <div className="flex flex-row gap-10">
                                <div className="flex flex-col gap-2">
                                    <p>Info: </p>
                                    <p>Price: {carInfo.price} NOK/day</p>
                                    <p>Location: {carInfo.location}</p>
                                    <p>Provider: {carInfo.provider.name}</p>
                                </div>

                                <div className="flex flex-col gap-2">
                                    <p>Extra Features: </p>
                                    <p>{carInfo.configuration.extraFeatures && carInfo.configuration.extraFeatures && carInfo.configuration.extraFeatures.name}</p>
                                    <p>{carInfo.configuration.extraFeatures && carInfo.configuration.extraFeatures && carInfo.configuration.extraFeatures.name}</p>
                                    <p>{carInfo.configuration.extraFeatures && carInfo.configuration.extraFeatures && carInfo.configuration.extraFeatures.name}</p>
                                    <p>{carInfo.configuration.extraFeatures && carInfo.configuration.extraFeatures && carInfo.configuration.extraFeatures.name}</p>
                                    <p>{carInfo.configuration.extraFeatures && carInfo.configuration.extraFeatures && carInfo.configuration.extraFeatures.name}</p>
                                </div>
                            </div>

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
                                <button onClick={handleClick} className="bg-[#326D0D] text-white font-bold py-2 px-4 rounded">Book now</button>

                            </div>
                        </CardFooter>
                    </div>
                ) : (
                    <CardHeader>
                        <CardTitle>Choose a car to see more details</CardTitle>
                    </CardHeader>
                )}
            </Card>
        </div>
    )
}

export default expandedCard;
