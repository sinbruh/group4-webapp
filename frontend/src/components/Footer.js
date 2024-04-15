import React from 'react';
import Image from "next/image";
import ntnu from "@/img/ntnu-logo.png";

export function Footer() {
    return (<footer>
            <Image src={ntnu} alt={"NTNU"} width={379} height={70}/>
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