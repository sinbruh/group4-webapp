'use client';
import React, { useState } from "react";
import Link from "next/link";
import logo from "@/img/temp-logo.png";
import Image from "next/image";
import { LoginModalClient } from "@/components/modal/LoginModal.client";
import { Button } from "@/components/ui/button";


import userIcon from "@/img/icons/person.svg";
export function Navigation() {

    let isLoginOpen, setIsLoginOpen;
    [isLoginOpen, setIsLoginOpen] = useState(false);

    return (
        <div id="nav-container" className=" p-2 bg-white min-h-16 flex items-center justify-between">
            <header className="flex items-center">
                <Link href="/">
                    <Image
                        src={logo}
                        alt="Temporary logo"
                        width={32}
                        height={32}
                    />
                </Link>
                <Link href="/">
                    <h1 className="px-2 font-bold">Rental Roulette</h1>
                </Link>
            </header>
            <nav className="flex items-center justify-center grow gap-x-10 m-10px">
                <p>
                    <Link href="/" className="p-2 rounded hover:bg-[#F1F5F9] font-bold">Home</Link>
                </p>
                <p>
                    <Link href="about" className="p-2 rounded hover:bg-[#F1F5F9] font-bold">About</Link>
                </p>
                <p>
                    <Link href="contact" className="p-2 rounded hover:bg-[#F1F5F9] font-bold">Contact</Link>
                </p>
            </nav>
            {/* User icon */}
            <Button variant="ghost" size="icon" onClick={() => setIsLoginOpen(true)}>
                <Image src={userIcon} alt="User icon" width={32} height={32} />
            </Button>
            {isLoginOpen && <LoginModalClient isOpen={isLoginOpen} onClose={() => setIsLoginOpen(false)} />}
        </div>
    )
}

export default Navigation;
