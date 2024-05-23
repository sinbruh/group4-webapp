import React, { useState, useEffect } from 'react';
import Locationbox from "@/components/ui/locationbox";
import DatePickerWithRange from "@/components/ui/daterange";
import PricePicker from "@/components/ui/pricePicker";
import {
    Popover,
    PopoverContent,
    PopoverTrigger,
} from "@/components/ui/popover"
import { useMediaQuery } from 'react-responsive';
import { Button } from "@/components/ui/button";
import FavoriteFilterButton from './FavoriteFilterButton';
import { isLoggedIn } from '@/tools/authentication';


export default function FilterBar({ defaultLocation, defaultStart, defaultEnd, setLocation, setDates, setPrice, onFavoriteFilterChange }) {
    const [location, setLocationState] = useState(defaultLocation);
    const [dates, setDatesState] = useState({ start: defaultStart, end: defaultEnd });
    const [price, setPriceState] = useState({ min: null, max: null });
    const [favoriteFilter, setFavoriteFilter] = useState(false); // Add this line

    console.log("FilterBar favoriteFilter", favoriteFilter);


    useEffect(() => {
        setLocation(location);
        setDates(dates);
        setPrice(price);
        onFavoriteFilterChange(favoriteFilter);
    }, [location, dates, price, favoriteFilter]);

    return (
        <div className="flex">
                <section className="flex grow flex-wrap justify-center gap-x-3 items-center bg-[#ffffff] rounded m-4 p-2">
                    <Locationbox defaultValue={defaultLocation} setLocation={setLocation} />
                    <DatePickerWithRange defaultStart={defaultStart} defaultEnd={defaultEnd} setDates={setDates} />
                    <PricePicker defaultValue={price} setPrice={setPrice} />
                    {isLoggedIn() && (
                        <>
                            <p> Favorites:</p>
                            <FavoriteFilterButton onFavoriteChange={setFavoriteFilter} />
                        </>
                    )}
                </section>
        </div>
    );
}
