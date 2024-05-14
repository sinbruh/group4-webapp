'use client'
import { useSearchParams } from 'next/navigation';
import styles from "./search.module.css";
import React from 'react';
import Navigation from "@/components/Navigation";
import { Footer } from "@/components/Footer";
import Link from "next/link";
import FilterBar from "@/components/FilterBar";
import CarCard from "@/components/CarCard";
import ExpandedCard from "@/components/ExpandedCard";
import { ScrollArea } from "@/components/ui/scroll-area";

export default function Search() {
    const searchParams = useSearchParams ();
    const location = searchParams.get('location');
    const start = searchParams.get('start');
    const end = searchParams.get('end');

    return (
        <div className="bg-[url('../img/temp-background-image.jpg')] bg-cover bg-center">
            <Navigation />
            <section className={styles.breadcrumb}>
                <p>
                    <Link href="/">Home</Link> &gt; <Link href="/search">Search</Link>
                </p>
            </section>
            <FilterBar defaultLocation={location} defaultStart = {start} defaultEnd = {end} />   
            <section className="flex flex-row justify-between h-screen px-2">

                <ScrollArea className="rounded-lg m-2 w-[45%] max-h-[78%]">
                    <CarCard carName="BMW-M3" price={500} location="stryn"
                        size={5} fuelType="diesel" transmission="manual"
                        description="1.5 liter" availability={true} />
                    <CarCard carName="BMW-M3" price={500} location="stryn"
                        size={5} fuelType="diesel" transmission="manual"
                        description="1.5 liter" availability={true} />
                    <CarCard carName="BMW-M3" price={500} location="stryn"
                        size={5} fuelType="diesel" transmission="manual"
                        description="1.5 liter" availability={true} />
                    <CarCard carName="BMW-M3" price={500} location="stryn"
                        size={5} fuelType="diesel" transmission="manual"
                        description="1.5 liter" availability={true} />
                </ScrollArea>

                <section className="rounded m-2 max-h-[78%] w-[55%]">
                    <ExpandedCard carName="BMW-M3" price={500} location="stryn"
                        size={5} fuelType="diesel" transmission="manual"
                        description="1.5 liter" availability={true} />
                </section>
            </section>
            <Footer />
        </div>
    );
};
