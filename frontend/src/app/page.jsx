'use client'
import { useRouter} from 'next/navigation';
import React, { useState} from "react";
import Locationbox from "@/components/ui/locationbox";
import { Button } from "@/components/ui/button";
import DatePickerWithRange from "@/components/ui/daterange";
import Navigation from "@/components/Navigation";
import { Footer } from "@/components/Footer";
import { format } from 'date-fns';

export default function Home() {
    const [user, setUser] = useState(null);
    const router = useRouter();
    const [location, setLocation] = useState('');
    const [dateRange, setDateRange] = useState({ from: '', to: '' });

    const handleSubmit = (e) => {
        e.preventDefault();
        // Navigate to the search page with query parameters
        router.push(`/search?location=${location}&start=${format(dateRange.from, 'T')}&end=${format(dateRange.to, 'T')}`);

        };


    return (
        <>
        <div className="bg-[url('../img/temp-background-image-low.webp')] bg-cover bg-center">
                <Navigation />

                <form onSubmit={handleSubmit} className="flex justify-center items-center h-screen gap-x-2">
                    <Locationbox value={location} onChange={setLocation} />
                    <DatePickerWithRange value={dateRange} onChange={setDateRange} />
                    <Button  type="submit" >
                        Search
                    </Button>
                </form>
            </div>
            <div className="bg-white text-center flex-row justify-center justify-items-center items-center">
                <div className="">
                    <div>
                        <h2 className="text-4xl p-10">How it works</h2>
                        <p>
                            Rental Roulette is a service that helps you find a car to rent. You
                            simply enter your location and the dates you need the car, and we will
                            find the best car for you. We will show you the car and the price, and
                            you can book it right away.
                        </p>
                    </div>
                    <div>
                        <h2 className="text-4xl p-10">Our reviews</h2>
                        <p>
                            "I was very happy with the car I got from Rental Roulette. It was a
                            great car and the price was very good." - John Doe
                        </p>
                        <p className="p-5">
                            "The service was very easy to use and I got a great car for my
                            vacation." - Jane Doe
                        </p>
                        <p className="pb-10">
                            "This is the best car rental service I have ever used. I will
                            definitely use it again." - Sten Oskar
                        </p>
                    </div>
                </div>

                <Footer />
            </div>
        </>
    );
}
