'use client'
import { useSearchParams } from 'next/navigation';
import React, { Suspense, useState, useEffect } from 'react';
import Navigation from "@/components/Navigation";
import { Footer } from "@/components/Footer";
import Link from "next/link";
import FilterBar from "@/components/FilterBar";
import ExpandedCard from "@/components/ExpandedCard";
import { ScrollArea } from "@/components/ui/scroll-area";
import { useMediaQuery } from 'react-responsive';
import { useRouter } from 'next/navigation';
import { createCarCards, filterCars, fetchData, flattenCars, useCarData } from '@/tools/carData.js';
import { useFavoriteStore, fetchFavorites } from '@/tools/favorite.js';
import { useStore } from '@/tools/authentication'

export default function Search() {
    const searchParams = useSearchParams();
    const defaultLocation = searchParams.get('location') || '';
    const start = searchParams.get('start') || new Date().toISOString().split('T')[0];
    const end = searchParams.get('end') || new Date(new Date().setDate(new Date().getDate() + 1)).toISOString().split('T')[0];
    const [favorites, addFavorite] = useFavoriteStore((state) => [state.favorites, state.addFavorite]);
    const user = useStore((state) => state.user);

    const [location, setLocation] = useState(defaultLocation);
    const [dates, setDates] = useState({ start: start, end: end });
    const [price, setPrice] = useState({ min: null, max: null });
    const [expandedCar, setExpandedCar] = useState(null);
    const [favoriteFilter, setFavoriteFilter] = useState(false);
    const router = useRouter();
    const isDesktop = useMediaQuery({ query: '(min-width: 1024px)' });
    const [cars, bookCar]  = useCarData((state) => [state.cars, state.bookCar]);
    const [filteredCars, setFilteredCars] = useState([]);
    const [dataLoaded, setDataLoaded] = useState(false);

    function showConfirmation() {
        router.push('/order');
    }

    // Use effect to fetch data when component mounts
    useEffect(() => {
        const fetchDataAndFilter = async () => {
            await fetchData();
            if (user) {
                const favorites = await fetchFavorites(user.email);
                if (favorites) {
                    favorites.forEach((favorite) => {
                        addFavorite(favorite.id);
                    });
                }
            }
            setDataLoaded(true); // Set dataLoaded to true after fetching data
        };

        fetchDataAndFilter();
    }, []);

    // Use effect to filter cars whenever dependencies change
    useEffect(() => {
        if (dataLoaded) {
            const flattenedCars = flattenCars(cars);
            const filtered = filterCars(flattenedCars, location, price, favoriteFilter, favorites);
            setFilteredCars(filtered);
        }
    }, [location, price, favoriteFilter, cars, dataLoaded, favorites]);

    return (
        <div className="bg-[url('/temp-background-image-low.webp')] bg-cover bg-center">
            <Suspense fallback={<div>Loading...</div>}>
                <Navigation />

                <section className="p-x-2">
                    <p>
                        <Link href="/">Home</Link> &gt; <Link href="/search">Search</Link>
                    </p>
                </section>
                {console.log(start + " " + end)}
                <FilterBar
                    defaultLocation={location}
                    defaultStart={dates.start}
                    defaultEnd={dates.end}
                    setLocation={setLocation}
                    setDates={setDates}
                    setPrice={setPrice}
                    onFavoriteFilterChange={setFavoriteFilter}
                />
                <section className="flex flex-row justify-between h-screen px-2">

                    <ScrollArea className="rounded-lg m-2 grow max-h-[78%]">
                        {createCarCards(filteredCars, setExpandedCar, dates)}
                    </ScrollArea>
                    {isDesktop &&
                        <section className="rounded m-2 max-h-[78%] w-[55%]">
                            <ExpandedCard carInfo={expandedCar} dates={dates}  showConfirmation={showConfirmation} />
                        </section>
                    }
                </section>
                <Footer />
            </Suspense>
        </div>
    );
}

