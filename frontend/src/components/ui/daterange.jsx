"use client";

import * as React from "react";
import { addDays, format, isValid } from "date-fns";
import { parse } from "date-fns";

import { Calendar as CalendarIcon } from "lucide-react";
import { DateRange } from "react-day-picker";

import { cn } from "@/lib/utils";
import { Button } from "@/components/ui/button";
import { Calendar } from "@/components/ui/calendar";
import {
  Popover,
  PopoverContent,
  PopoverTrigger,
} from "@/components/ui/popover";

export default function DatePickerWithRange({
  className,
  defaultStart,
  defaultEnd,
  setDates,
  value,
  onChange,
}) {
  const [date, setDate] = React.useState({
    from: defaultStart
      ? parse(defaultStart, "T", new Date())
      : new Date(Date.now()),
    to: defaultEnd
      ? parse(defaultEnd, "T", new Date())
      : addDays(new Date(Date.now()), 7),
  });

  const timer = React.useRef(null);
  const clicks = React.useRef(0);

  const handleSingleClick = (day) => {
    if (!date) {
      setDate({ from: day, to: undefined });
    } else if (date && !date.to) {
      if (day.getTime() < date.from.getTime()) {
        setDate({ from: day, to: undefined });
      } else {
        setDate({ from: date.from, to: day });
      }
    } else if (date && date.to) {
      const diffFrom = Math.abs(day.getTime() - date.from.getTime());
      const diffTo = Math.abs(day.getTime() - date.to.getTime());

      if (diffFrom < diffTo) {
        setDate({ from: day, to: date.to });
      } else {
        setDate({ from: date.from, to: day });
      }
    }
  };

  const handleDoubleClick = (day) => {
    const endDate = new Date(day.getTime());

    endDate.setDate(day.getDate() + 7);

    setDate({ from: day, to: endDate });
  };

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
              "justify-start text-left font-normal",
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
                isValid(new Date(date.from)) &&
                format(new Date(date.from), "LLL dd, y")
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
            onDayClick={(day) => {
              if (clicks.current === 0) {
                clicks.current++;
                handleSingleClick(day);

                timer.current = setTimeout(() => {
                  clicks.current = 0;
                }, 250);
              } else if (clicks.current === 1) {
                clearTimeout(timer.current);
                clicks.current = 0;
                handleDoubleClick(day);
              }
            }}
            numberOfMonths={2}
            weekStartsOn={1}
          />
        </PopoverContent>
      </Popover>
    </div>
  );
}
