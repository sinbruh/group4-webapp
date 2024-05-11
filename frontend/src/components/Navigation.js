import React from "react";
import Link from "next/link";
import logo from "@/img/temp-logo.png";
import Image from "next/image";
import userIcon from "@/img/icons/person.svg";
export function Navigation() {
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
                    <b>
                        <Link href="/">Home</Link>
                    </b>
                </p>
                <p>
                    <b>
                        <Link href="about">About</Link>
                    </b>
                </p>
                <p>
                    <b>
                        <Link href="contact">Contact</Link>
                    </b>
                </p>
            </nav>
            {/* User icon */}
        <Image src={userIcon} alt="User icon" width={32} height={32} />
        </div>
    )
}

export default Navigation;
