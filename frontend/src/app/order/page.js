import React from 'react';
import Navigation from "@/components/Navigation";
import { Footer } from "@/components/Footer";
import Image from "next/image";
import carbackground from "/img/carbackground.png";

export default function Page(carInfo) {
    return (
        <div className="min-h-screen flex flex-col justify-between bg-gray-100">
            <Navigation />
                <div className="container px-5 mx-auto">
                    <div className="w-full flex flex-wrap justify-center mb-12">
                        <div className="flex flex-col items-start content-start mb-12 w-full md:w-1/3 px-4">
                            <h4 className="text-2xl font-semibold text-gray-900 mb-4">Thank you for your Order!</h4>
                            <p className="leading-relaxed text-base">We will be in touch with you shortly to confirm your order and provide you with further details.</p>

                        </div>
                    </div>
                </div>
            <Footer />
        </div>
    );
}
