import React from 'react';
import Image from "next/image";
import ntnu from "@/img/ntnu-logo.png";

export function Footer() {
    return (<footer className="bg-[#00509E] gap-y-10px flex-row justify-center items-center" >
        <div className="flex flex-row justify-center items-center">
            <Image src={ntnu} alt={"NTNU"} width={379} height={70}/>
        </div>
            <p>
                <em>
                    This website is a result of a university group project, performed in
                    the course <a href="https://www.ntnu.edu/studies/courses/IDATA2301#tab=omEmnet">
                        IDATA2301 Web
                        <br/> Technologies </a>, at <a href="https://www.ntnu.edu/">NTNU</a>. All the information
                    provided here is a result of imagination. Any
                    <br/>
                    resemblance with real companies or products is a coincidence.
                </em>
            </p>
        </footer>)
}
