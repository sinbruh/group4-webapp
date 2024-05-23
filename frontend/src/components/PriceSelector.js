import React from "react";

import {
    Select,
    SelectContent,
    SelectItem,
    SelectTrigger,
    SelectValue,
} from "@/components/ui/select"

export default function PriceSelector({ providers, onValueChange }) {
    return (
        <Select onValueChange={onValueChange} >
            <SelectTrigger className="w-[180px]">
                <SelectValue placeholder="Price" />
            </SelectTrigger>
            <SelectContent>
                {providers.map((provider) => (
                    <SelectItem key={provider.id} value={provider.id}>
                        {provider.name + ": " + provider.price + " NOK"}
                    </SelectItem>
                ))}
            </SelectContent>
        </Select>
    )
}
