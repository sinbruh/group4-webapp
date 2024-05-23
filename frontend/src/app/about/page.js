import React from 'react';
import Navigation from "@/components/Navigation";
import { Footer } from "@/components/Footer";
import Image from "next/image";
import carbackground from "/img/carbackground.png";
import Head from "next/head";

export default function Page() {
    return (
        <>
            <Head>
                <title>About Us page</title>
            </Head>
            <div className="min-h-screen flex flex-col justify-between bg-gray-100">
                <Navigation/>
                <div className="flex-grow"></div>
                <div className="relative flex justify-center items-center mb-12">
                    <Image src={carbackground} alt="About Us" layout="responsive"
                           className="w-full h-auto object-cover"/>
                    <div className="absolute inset-0 flex justify-center items-center bg-black bg-opacity-50">
                        <h1 className="title-font font-semibold text-5xl text-white">About Us</h1>
                    </div>
                </div>
                <section className="text-gray-900 body-font w-full py-16 bg-white shadow-lg">
                    <div className="container px-5 mx-auto">
                        <div className="w-full flex flex-wrap justify-center mb-12">
                            <div className="flex flex-col items-start mb-12 w-full md:w-1/3 px-4">
                                <p className="leading-relaxed text-lg text-gray-900">
                                    Welcome to Rental Roulette, where your journey begins with a spin of the wheel and
                                    ends with
                                    the perfect ride tailored just for you. At Rental Roulette, we've revolutionized the
                                    car rental
                                    experience by aggregating prices from a myriad of trusted third-party car rental
                                    companies. No
                                    more hopping from site to site in search of the best deal &mdash; our platform puts
                                    the power
                                    back in your hands. Whether you're embarking on a spontaneous road trip, planning a
                                    family
                                    vacation, or simply need a reliable set of wheels for your daily commute, Rental
                                    Roulette
                                    ensures that you're always in the driver's seat when it comes to choice and
                                    affordability.
                                </p>
                            </div>
                            <div className="flex flex-col items-start mb-12 w-full md:w-1/3 px-4">
                                <p className="leading-relaxed text-lg text-gray-900">
                                    Our commitment extends beyond just providing competitive prices; we prioritize
                                    transparency,
                                    convenience, and a touch of excitement. With our user-friendly interface, you can
                                    effortlessly
                                    compare options, finding the ideal vehicle to suit your preferences and budget. Say
                                    goodbye to
                                    hidden fees and hello to a seamless rental experience. At Rental Roulette, we
                                    believe that
                                    every journey should begin with a sense of adventure, and our platform is designed
                                    to add an
                                    element of thrill to the otherwise mundane task of renting a car. Join us in the
                                    pursuit of
                                    hassle-free, cost-effective, and enjoyable travels &mdash; where every rental is a
                                    winning
                                    spin!
                                </p>
                            </div>
                            <div className="flex flex-col items-start mb-12 w-full md:w-1/3 px-4">
                                <p className="leading-relaxed text-lg text-gray-900">
                                    Nestled in the picturesque landscapes of Ålesund, Rental Roulette takes pride in
                                    catering
                                    specifically to the vibrant community of this stunning coastal town. Our focus is on
                                    providing
                                    tailored car rental solutions by aggregating prices exclusively from rental
                                    companies in the
                                    Ålesund area. Whether you're exploring the fjords, embarking on a scenic coastal
                                    drive, or
                                    simply navigating the charming streets of Ålesund itself, Rental Roulette ensures
                                    that your
                                    local car rental experience is not only convenient but also intimately connected to
                                    the unique
                                    beauty of this Norwegian gem. Embrace the essence of Ålesund with us, where every
                                    rental is a
                                    seamless blend of convenience and local charm.
                                </p>
                            </div>
                            <div className="flex flex-col items-start mb-12 w-full md:w-1/3 px-4">
                                <h3 className="text-gray-900 text-2xl font-medium title-font mb-3">Address</h3>
                                <p className="leading-relaxed text-lg text-gray-900">
                                    Vestre Olsvikveg 13, 6019 Ålesund, Norway
                                </p>
                            </div>
                        </div>
                    </div>
                </section>
                <Footer/>
            </div>
        </>
    );
}
