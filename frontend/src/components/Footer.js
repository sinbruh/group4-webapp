import React from 'react';
import Image from "next/image";
import ntnu from "/public/ntnu-logo.webp";
import Link from "next/link";

export function Footer() {
    return (<footer className="bg-[#00509E] gap-y-10px p-5 flex-row justify-center items-center" >
        <div className="flex flex-row justify-center items-center">
            <Image src={ntnu} alt={"NTNU"} width={379} height={70}/>
        </div>
            <p className="text-center">
                <em className="text-white">
                    This website is a result of a university group project, performed in
                    the course <Link className="text-blue-300 underline" href="https://www.ntnu.edu/studies/courses/IDATA2301#tab=omEmnet">
                        IDATA2301 Web
                        <br/> Technologies </Link>, at <Link href="https://www.ntnu.edu/">NTNU</Link>. All the information
                    provided here is a result of imagination. Any
                    <br/>
                    resemblance with real companies or products is a coincidence.
                </em>
            </p>
        </footer>)
}
