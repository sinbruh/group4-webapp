'use client'
import { useSearchParams } from 'next/navigation';
import styles from "./search.module.css";
import React, { useEffect, useState } from 'react';
import Navigation from "@/components/Navigation";
import { Footer } from "@/components/Footer";
import Link from "next/link";
import FilterBar from "@/components/FilterBar";
import ExpandedCard from "@/components/ExpandedCard";
import { ScrollArea } from "@/components/ui/scroll-area";
import CarReader from './CarReader.js';

export default function Search() {
    const searchParams = useSearchParams ();
    const defaultLocation = searchParams.get('location');
    const start = searchParams.get('start');
    const end = searchParams.get('end');

    const [location, setLocation] = useState(defaultLocation);
    const [dates, setDates] = useState({start: start, end: end});
    const [price, setPrice] = useState({min: null, max: null});
    const [expandedCar, setExpandedCar] = useState(null);

    console.log('Location in page:', location);
    console.log('Dates in page:', dates);
    console.log('Price in page:', price);

    return (
        <div className="bg-[url('/temp-background-image-low.webp')] bg-cover bg-center">
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
            />
            <section className="flex flex-row justify-between h-screen px-2">

                <ScrollArea className="rounded-lg m-2 w-[45%] max-h-[78%]">
                    <CarReader
                    location={location}
                    dates={dates}
                    price={price}
                    setExpandedCarInfo={setExpandedCar}
                    />
                </ScrollArea>

                <section className="rounded m-2 max-h-[78%] w-[55%]">
                    <ExpandedCard carInfo={expandedCar} />
                </section>
            </section>
            <Footer />
        </div>
    );
};
