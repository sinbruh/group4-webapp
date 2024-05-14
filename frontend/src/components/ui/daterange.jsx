"use client"

import * as React from "react"
import { addDays, format, isValid } from "date-fns"
import { parse } from 'date-fns';

import { Calendar as CalendarIcon } from "lucide-react"
import { DateRange } from "react-day-picker"

import { cn } from "@/lib/utils"
import { Button } from "@/components/ui/button"
import { Calendar } from "@/components/ui/calendar"
import {
    Popover,
    PopoverContent,
    PopoverTrigger,
} from "@/components/ui/popover"

export default function DatePickerWithRange({ className, defaultStart, defaultEnd, setDates, value, onChange }) {

    const [date, setDate] = React.useState({
        from: defaultStart ? parse(defaultStart, 'T', new Date()) : new Date(Date.now()),
        to: defaultEnd ? parse(defaultEnd, 'T', new Date()) : addDays(new Date(Date.now()), 7),
    });

    React.useEffect(() => {
        if (onChange) {
            onChange(date);
        }
        if (setDates) {
            setDates(date);
        }
    }, [date]);

    return (
        <div className={cn("grid gap-2", className)}>
            <Popover>
                <PopoverTrigger asChild>
                    <Button
                        id="date"
                        variant={"outline"}
                        className={cn(
                            "w-[300px] justify-start text-left font-normal",
                            !date && "text-muted-foreground"
                        )}
                    >
                        <CalendarIcon className="mr-2 h-4 w-4" />
                        {date?.from ? (
                            date.to ? (
                                <>
                                    {format(date.from, "LLL dd, y")} -{" "}
                                    {format(date.to, "LLL dd, y")}
                                </>
                            ) : (
                                isValid(new Date(date.from)) && format(new Date(date.from), "LLL dd, y")
                            )
                        ) : (
                            <span>Pick a date</span>
                        )}
                    </Button>
                </PopoverTrigger>
                <PopoverContent className="w-auto p-0" align="start">
                    <Calendar
                        initialFocus
                        mode="range"
                        defaultMonth={date?.from}
                        selected={date}
                        onSelect={setDate}
                        numberOfMonths={2}
                    />
                </PopoverContent>
            </Popover>
        </div>
    )
}

