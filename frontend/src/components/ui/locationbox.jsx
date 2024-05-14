"use client"

import * as React from "react"
import { Check, ChevronsUpDown } from "lucide-react"

import { cn } from "@/lib/utils"
import { Button } from "@/components/ui/button"
import {
    Command,
    CommandEmpty,
    CommandGroup,
    CommandInput,
    CommandItem,
} from "@/components/ui/command"
import {
    Popover,
    PopoverContent,
    PopoverTrigger,
} from "@/components/ui/popover"

const locations = [
    {
        value: "ålesund",
        label: "Ålesund",
    },
    {
        value: "stryn",
        label: "Stryn",
    },
    {
        value: "alta",
        label: "Alta",
    },
    {
        value: "oslo",
        label: "Oslo",
    },
    {
        value: "stavanger",
        label: "Stavanger",
    },
]

export default function Locationbox({ defaultValue, onChange}) {
    const [value, setValue] = React.useState(defaultValue || false)
    const [open, setOpen] = React.useState("")
    

    const handleSelect = (locationValue) => {
        setValue(locationValue);
        onChange(locationValue);
    };


    

    return (
        <Popover open={open} onOpenChange={setOpen}>
            <PopoverTrigger asChild>
                <Button
                    variant="outline"
                    role="combobox"
                    aria-expanded={open}
                    className="w-[200px] justify-between"
                >
                    {value
                        ? locations.find((location) => location.value === value)?.label
                        : "Select location..."}
                    <ChevronsUpDown className="ml-2 h-4 w-4 shrink-0 opacity-50" />
                </Button>
            </PopoverTrigger>
            <PopoverContent className="w-[200px] p-0">
                <Command>
                    <CommandInput placeholder="Search location..." />
                    <CommandEmpty>No location found.</CommandEmpty>
                    <CommandGroup>
                        {locations.map((location) => (
                           <CommandItem
                            key={location.value}
                            value={location.value}
                            onSelect={() => {
                                handleSelect(location.value);
                                setOpen(false);
                            }}
                       >
                           <Check
                               className={cn(
                                   "mr-2 h-4 w-4",
                                   value === location.value ? "opacity-100" : "opacity-0"
                               )}
                           />
                           {location.label}
                       </CommandItem>
                        ))}
                    </CommandGroup>
                </Command>
            </PopoverContent>
        </Popover>
    )
}
