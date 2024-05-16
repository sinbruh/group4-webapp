import React from "react";

import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "@/components/ui/select"

export default function PriceSelector(providers) {
    console.log(providers)
    return (
        <Select>
  <SelectTrigger className="w-[180px]">
    <SelectValue placeholder="Price" />
  </SelectTrigger>
  <SelectContent>
    {providers.providers.map((provider) => (
        <SelectItem value={provider.id}>
            {provider.name + ": " + provider.price + " NOK"}
        </SelectItem>
        ))}
  </SelectContent>
</Select>
    )}
