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
    const isDesktop = useMediaQuery({ query: '(min-width: 1450px)' });

    console.log("FilterBar favoriteFilter", favoriteFilter);


    useEffect(() => {
        setLocation(location);
        setDates(dates);
        setPrice(price);
        onFavoriteFilterChange(favoriteFilter);
    }, [location, dates, price, favoriteFilter]);

    return (
        <div className="flex">
            {isDesktop &&
                <section className="flex grow gap-2 items-center bg-[#ffffff] rounded m-4 p-2">
                    <Locationbox defaultValue={defaultLocation} setLocation={setLocation} />
                    <DatePickerWithRange defaultStart={defaultStart} defaultEnd={defaultEnd} setDates={setDates} />
                    <PricePicker defaultValue={price} setPrice={setPrice} />
                    {isLoggedIn() && (
                        <>
                            <p> Filter by favorite: </p>
                            <FavoriteFilterButton onFavoriteChange={setFavoriteFilter} />
                        </>
                     )}
                </section>

            }
            {!isDesktop &&
                <Popover>
                    <PopoverTrigger className="grow flex m-4">
                        <Button className="grow">Filter</Button>
                    </PopoverTrigger>
                    <PopoverContent>
                        <section className="flex flex-col gap-2 items-center bg-[#ffffff] rounded m-4 p-2">
                            <Locationbox defaultValue={defaultLocation} setLocation={setLocation} />
                            <DatePickerWithRange defaultStart={defaultStart} defaultEnd={defaultEnd} setDates={setDates} />
                            <PricePicker defaultValue={price} setPrice={setPrice} />
                            {isLoggedIn() && (
                                <>
                                    <p> Filter by favorite: </p>
                                    <FavoriteFilterButton onFavoriteChange={setFavoriteFilter} />
                                </>
                            )}
                        </section>
                    </PopoverContent>
                </Popover>
            }
        </div>
    );
}
