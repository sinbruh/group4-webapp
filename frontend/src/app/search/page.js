'use client'
import { useSearchParams } from 'next/navigation';
import styles from "./search.module.css";
import React, { Suspense, useState } from 'react';
import Navigation from "@/components/Navigation";
import { Footer } from "@/components/Footer";
import Link from "next/link";
import FilterBar from "@/components/FilterBar";
import ExpandedCard from "@/components/ExpandedCard";
import { ScrollArea } from "@/components/ui/scroll-area";
import CarReader from '../../components/CarReader.js';
import { useMediaQuery } from 'react-responsive';
import {  useRouter } from 'next/navigation';


export default function Search() {
    const searchParams = useSearchParams();
    const defaultLocation = searchParams.get('location') || '';
    const start = searchParams.get('start') || new Date().toISOString().split('T')[0];
    const end = searchParams.get('end') || new Date(new Date().setDate(new Date().getDate() + 1)).toISOString().split('T')[0];
    const isDesktop = useMediaQuery({ query: '(min-width: 1450px)' }) || true;

    const [location, setLocation] = useState(defaultLocation);
    const [dates, setDates] = useState({ start: start, end: end });
    const [price, setPrice] = useState({ min: null, max: null });
    const [expandedCar, setExpandedCar] = useState(null);
    const [favoriteFilter, setFavoriteFilter] = useState(false);
    const router = useRouter();

    function showConfirmation () {
        router.push('/order');
    }

    return (
        <div className="bg-[url('/temp-background-image-low.webp')] bg-cover bg-center">
            <Suspense fallback={<div>Loading...</div>}>
                <Navigation />

                <section className={styles.breadcrumb}>
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
                        <CarReader
                            location={location}
                            dates={dates}
                            price={price}
                            setExpandedCarInfo={setExpandedCar}
                            favoriteFilter={favoriteFilter}
                        />
                    </ScrollArea>
                    {isDesktop &&
                        <section className="rounded m-2 max-h-[78%] w-[55%]">
                            <ExpandedCard carInfo={expandedCar} dates={dates} showConfirmation={showConfirmation} />
                        </section>
                    }
                </section>
                <Footer />
            </Suspense>
        </div>
    );
};
