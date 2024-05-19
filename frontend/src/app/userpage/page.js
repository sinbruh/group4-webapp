'use client'
import React from 'react';
import { useEffect } from 'react';
import Navigation from "@/components/Navigation";
import { Footer } from "@/components/Footer";
import Link from "next/link";
import {isAdmin, isUser, getAuthenticatedUser} from "@/tools/authentication.js"
import AdminProfileSettings from "@/components/AdminProfileSettings.js"
import UserProfileSettings from "@/components/UserProfileSettings.js"

export default function Page() {
    const user = getAuthenticatedUser();

    useEffect(() => {
        if (!isAdmin(user) && !isUser(user)) {
            console.log("log in");
        }
    }, []);

    return (
        <div className="bg-[url('/temp-background-image-low.webp')] bg-cover bg-center">
            <Navigation />
            <section className="text-black">
                <p>
                    <Link href="/">Home</Link> &gt; <Link href="/userpage">User Page</Link>
                </p>
            </section>
            <div className="flex items-center justify-center min-h-screen">
                <section className="m-2 p-2 bg-white h-dvh w-4/5 rounded flex">
                    <div className="w-1/4 h-full bg-red-200">
                        <button className="py-2 px-4 bg-blue-500 text-white rounded">Button 1</button>
                        <button className="py-2 px-4 bg-blue-500 text-white rounded">Button 2</button>
                        <button className="py-2 px-4 bg-blue-500 text-white rounded">Button 3</button>
                    </div>

                    <div className="w-3/4 h-full bg-blue-200">
                    <p>Example text</p>
                    </div>
                </section>
            </div>
            <Footer />
        </div>
    );
};