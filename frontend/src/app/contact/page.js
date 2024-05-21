import React from 'react';
import Navigation from "@/components/Navigation";
import { Footer } from "@/components/Footer";
import Link from "next/link";

export default function Page() {
    return (
        <div className="bg-[url('/temp-background-image-low.webp')] bg-cover bg-center min-h-screen flex flex-col justify-between">
            <Navigation />
            <section className="text-gray-100 body-font">
                <div className="container px-5 py-24 mx-auto flex flex-col items-center">
                    <div className="w-full lg:w-2/3 bg-white bg-opacity-80 rounded-lg p-8 shadow-lg">
                        <h1 className="title-font font-semibold text-4xl text-gray-900 mb-4">Contact Us</h1>
                        <p className="leading-relaxed mt-4 text-lg bg-opacity-50 p-6 rounded-lg text-gray-900 mb-6">
                            Have a question, concern, or feedback? We'd love to hear from you! Feel free to reach out to us using the contact information provided below, and we'll get back to you as soon as possible.
                        </p>
                        <div className="mb-6">
                            <h2 className="text-gray-900 text-xl font-medium title-font mb-3">Technical Support</h2>
                            <p className="leading-relaxed text-base text-gray-900">
                                In case of any technical issues, please contact our support team at <span className="font-semibold">999 99 999</span>. Our team is available 24/7 to assist you with any troubleshooting or inquiries if you encounter any difficulties while using our platform.
                            </p>
                        </div>
                        <div className="mb-6">
                            <h3 className="text-gray-900 text-xl font-medium title-font mb-3">Media Inquiries</h3>
                            <p className="leading-relaxed text-base text-gray-900">
                                For media inquiries, please contact our press team at <span className="font-semibold">999 99 999</span>. Our team is available Monday through Friday, from 9:00 AM to 5:00 PM.
                            </p>
                        </div>
                        <div className="mb-6">
                            <h3 className="text-gray-900 text-xl font-medium title-font mb-3">Address</h3>
                            <p className="leading-relaxed text-base text-gray-900">
                                Vestre Olsvikveg 13, 6019 Ålesund, Norway
                            </p>
                        </div>
                    </div>
                </div>
            </section>
            <Footer />
        </div>
    );
};
