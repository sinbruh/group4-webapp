'use client';
import React, {useState} from "react";
import Link from "next/link";
import logo from "@/img/temp-logo.png";
import Image from "next/image";
import { LoginModalClient } from "@/app/modal/LoginModal.client";



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
                    <b className="p-2 rounded hover:bg-gray-200">
                        <Link href="/">Home</Link>
                    </b>
                </p>
                <p>
                    <b className="p-2 rounded hover:bg-gray-200">
                        <Link href="about">About</Link>
                    </b>
                </p>
                <p>
                    <b className="p-2 rounded hover:bg-gray-200">
                        <Link href="contact">Contact</Link>
                    </b>
                </p>
            </nav>
            {/* User icon */}
            <button
                className="btn btn-primary text-white bg-blue-950 hover:bg-blue-800 font-bold py-2 px-4 rounded-full"
                onClick={() => setIsLoginOpen(true)}
            >
                Login
            </button>
            {isLoginOpen && <LoginModalClient isOpen={isLoginOpen} onClose={() => setIsLoginOpen(false)}/>}
        <Image src={userIcon} alt="User icon" width={32} height={32} />
        </div>
    )
}

export default Navigation;
