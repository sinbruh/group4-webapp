"use client"
import React, {useState} from "react";
import favoriteIcon from "@/img/icons/star.svg";
import favoriteIconOutline from "@/img/icons/star-outline.svg";
import { Button } from "@/components/ui/button";
import Image from "next/image";

export default function FavoriteButton() {
    const [isFavorite, setIsFavorite] = useState(false);

    return (
        <Button variant="ghost" size="icon" onClick={() => setIsFavorite(!isFavorite)}>
            <Image src={isFavorite ? favoriteIcon : favoriteIconOutline} width={32} height={32} alt="favorite" />
        </Button>
    );
}
