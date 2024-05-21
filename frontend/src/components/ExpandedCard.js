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

import {
    Card,
    CardContent,
    CardDescription,
    CardFooter,
    CardHeader,
    CardTitle,
} from "@/components/ui/card"

import { useStore } from "@/tools/authentication"

const expandedCard = ({ carInfo, dates }) => {
    const user = useStore((state) => state.user);
    const [isOpen, setIsOpen] = React.useState(false);
    const confirmationDialogRef = React.useRef();

    let carImage;
    if (carInfo && carInfo.carImageInput) {
        carImage = carInfo.carImageInput ? `/carsLowResWEBP/${carInfo.carImageInput}` : carDefaultImage;
    }

    const handleClick = async () => {
        console.log("Book now button clicked")
        console.log("User: ", user)

        if (user) {
            const bookingConfirmed = await confirmationDialogRef.current.openDialog();
            if (bookingConfirmed) {
                console.log("Dates: ", dates.start, dates.end)

                const body = {
                    startDate: dates.start,
                    endDate: dates.end,
                }

                const response = await asyncApiRequest("POST", "/api/rentals/" + user.email + "/" + carInfo.provider[0].id, body)
                if (response) {
                    console.log("Booking response: ", response)
                } else {
                    console.log("Booking successful")
                }
            }


        } else {
            setIsOpen(true);
        }
    }

    return (
        <div className="h-full">
            <ConfirmationAlert ref={confirmationDialogRef} />
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
                                <div className="text-[#326D0D] font-bold flex gap-2 items-center"><Image src={checkIcon} width={32} height={32} /> Available </div> :
                                <div className="text-red-500 font-bold flex gap-2 items-center"><Image src={closeIcon} width={32} height={32} /> Unavailable </div>}
                            <div className="flex flex-row gap-10">
                                <div className="flex flex-col gap-2">
                                    <p>Info: </p>
                                    <p>Price: {carInfo.price} NOK/day</p>
                                    <p>Location: {carInfo.location}</p>
                                    <p>Provider: {carInfo.provider[0].name}</p>
                                </div>

                                <div className="flex flex-col gap-2">
                                    <p>Extra Features: </p>
                                    <p>{carInfo.configurations[0].extraFeatures && carInfo.configurations[0].extraFeatures[0] && carInfo.configurations[0].extraFeatures[0].name}</p>
                                    <p>{carInfo.configurations[0].extraFeatures && carInfo.configurations[0].extraFeatures[1] && carInfo.configurations[0].extraFeatures[1].name}</p>
                                    <p>{carInfo.configurations[0].extraFeatures && carInfo.configurations[0].extraFeatures[2] && carInfo.configurations[0].extraFeatures[2].name}</p>
                                    <p>{carInfo.configurations[0].extraFeatures && carInfo.configurations[0].extraFeatures[3] && carInfo.configurations[0].extraFeatures[3].name}</p>
                                    <p>{carInfo.configurations[0].extraFeatures && carInfo.configurations[0].extraFeatures[4] && carInfo.configurations[0].extraFeatures[4].name}</p>
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
