import React from "react";
import Locationbox from "@/components/ui/locationbox";
import { Button } from "@/components/ui/button";
import DatePickerWithRange from "@/components/ui/daterange";
import Link from "next/link";
import Navigation from "@/components/Navigation";
import { Footer } from "@/components/Footer";

export default function Home() {
    return (
        <div className="bg-[url('../img/temp-background-image.jpg')] bg-cover bg-center">
            <Navigation />

            <div className="flex justify-center items-center h-screen gap-x-2">
                <Locationbox />
                <DatePickerWithRange />
                <Button asChild>
                    <Link href="/search">Search</Link>
                </Button>
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
        </div>
    );
}
