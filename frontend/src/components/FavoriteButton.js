"use client"
import React, { useState } from "react";
import favoriteIcon from "@/img/icons/star.svg";
import favoriteIconOutline from "@/img/icons/star-outline.svg";
import { Button } from "@/components/ui/button";
import Image from "next/image";
import {
    Tooltip,
    TooltipContent,
    TooltipProvider,
    TooltipTrigger,
} from "@/components/ui/tooltip";

import { useStore } from "@/tools/authentication";
import { UserNotLoggedInAlert } from "@/components/alerts/UserNotLoggedInAlert";

export default function FavoriteButton({ configID }) {
    const [isFavorite, setIsFavorite] = useState(false);
    const user = useStore((state) => state.user);
    const [isAlertOpen, setIsAlertOpen] = useState(false);

    const handleOnClick = (e) => {
        console.log("Favorite button clicked");
        e.stopPropagation();

        if (user) {
            setIsFavorite(!isFavorite);
            //send request to backend to add to favorite with configID
        } else {
            console.log("User not logged in");
            setIsAlertOpen(true);
        }
    };

    return (
        <div>
            <UserNotLoggedInAlert isOpen={isAlertOpen} setIsOpen={setIsAlertOpen} />
            <TooltipProvider>
                <Tooltip>
                    <TooltipTrigger asChild>
                        <Button variant="ghost" size="icon" onClick={(e) => handleOnClick(e)}>
                            <Image src={isFavorite ? favoriteIcon : favoriteIconOutline} width={32} height={32} alt="favorite" />
                        </Button>
                    </TooltipTrigger>
                    <TooltipContent>
                        <p>Add to favorite</p>
                    </TooltipContent>
                </Tooltip>
            </TooltipProvider>
        </div>
    );
}
