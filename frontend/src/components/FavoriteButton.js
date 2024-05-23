"use client"
import React, { useState, useEffect } from "react";
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
import { sendFavoriteRequest } from "@/tools/favorite";

import { useStore } from "@/tools/authentication";
import { UserNotLoggedInAlert } from "@/components/alerts/UserNotLoggedInAlert";

import { useFavoriteStore } from "@/tools/favorite";


export default function FavoriteButton(carInfo) {
    const [favorites, addFavorite, removeFavorite] = useFavoriteStore((state) => [state.favorites, state.addFavorite, state.removeFavorite]);
    const [isFavorite, setIsFavorite] = useState(false);
    const user = useStore((state) => state.user);
    const [isAlertOpen, setIsAlertOpen] = useState(false);

    useEffect(() => {
        if (favorites && Array.isArray(favorites)) {
            setIsFavorite(favorites.includes(carInfo.carInfo.provider.id));
        }
    }, [favorites, carInfo]);

    const handleOnClick = (e) => {
        e.stopPropagation();

        if (user) {
            if (isFavorite) {
                removeFavorite(carInfo.carInfo.provider.id);
            } else {
                addFavorite(carInfo.carInfo.provider.id);
            }

            setIsFavorite(!isFavorite);

            sendFavoriteRequest(carInfo.carInfo.provider.id);

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
