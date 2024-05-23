import React from 'react';
import Navigation from "@/components/Navigation";
import { Footer } from "@/components/Footer";
import Image from "next/image";
import contact from "/img/contact.png";
import Head from "next/head";

export default function Page() {
    return (
        <>
            <Head>
                <title>Contact Us page</title>
            </Head>
            <div className="min-h-screen flex flex-col justify-between bg-gray-100">
                <Navigation/>
                <div className="flex-grow"></div>
                <div className="relative flex justify-center items-center mb-8">
                    <Image src={contact} alt="Contact" width={1550} height={80} className="w-full h-auto"/>
                    <div className="absolute inset-0 flex justify-center items-center">
                        <h1 className="title-font font-semibold text-6xl text-white">Contact Us</h1>
                    </div>
                </div>
                <section className="text-gray-900 body-font w-full py-12 mt-auto">
                    <div className="container px-5 mx-auto flex flex-col items-center">
                        <div className="w-full flex flex-wrap justify-center">
                            <div className="flex flex-col items-start mb-6 w-full md:w-1/3 px-4">
                                <p className="leading-relaxed text-lg text-gray-900 mb-6">
                                    Have a question, concern, or feedback? We'd love to hear from you!
                                </p>
                                <p className="leading-relaxed text-lg text-gray-900 mb-6">
                                    Feel free to reach out to us using the contact information provided below, and we'll
                                    get back to you as soon as possible.
                                </p>
                            </div>
                            <div className="flex flex-col items-start mb-6 w-full md:w-1/3 px-4">
                                <h2 className="text-gray-900 text-xl font-medium title-font mb-3">Technical Support</h2>
                                <p className="leading-relaxed text-base text-gray-900">
                                    In case of any technical issues, please contact our support team at <span
                                    className="font-semibold">999 99 999</span>.
                                </p>
                                <p className="leading-relaxed text-base text-gray-900">
                                    Our team is available 24/7 to assist you with any troubleshooting or inquiries if
                                    you encounter any difficulties while using our platform.
                                </p>
                            </div>
                            <div className="flex flex-col items-start mb-6 w-full md:w-1/3 px-4">
                                <h3 className="text-gray-900 text-xl font-medium title-font mb-3">Media Inquiries</h3>
                                <p className="leading-relaxed text-base text-gray-900">
                                    For media inquiries, please contact our press team at <span
                                    className="font-semibold">999 99 999</span>.
                                </p>
                                <p className="leading-relaxed text-base text-gray-900">
                                    Our team is available Monday through Friday, from 9:00 AM to 5:00 PM.
                                </p>
                            </div>
                            <div className="flex flex-col items-start mb-6 w-full md:w-1/3 px-4">
                                <h3 className="text-gray-900 text-xl font-medium title-font mb-3">Address</h3>
                                <p className="leading-relaxed text-base text-gray-900">
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
