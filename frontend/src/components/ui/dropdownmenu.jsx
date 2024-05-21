import {
    LogOut,
    User,
} from "lucide-react"

import { Button } from "@/components/ui/button"

import {
    DropdownMenu,
    DropdownMenuContent,
    DropdownMenuGroup,
    DropdownMenuItem,
    DropdownMenuLabel,
    DropdownMenuSeparator,
    DropdownMenuTrigger,
} from "@/components/ui/dropdown-menu"
import Link from "next/link";
import Image from "next/image";
import userIcon from "@/img/icons/person.svg";
import React from "react";
import {deleteAuthorizationCookies, useStore} from "@/tools/authentication";

export function DropDownMenu() {
    const logout = useStore((state) => state.logout);


    function handleLogout() {
        deleteAuthorizationCookies();
        logout();
        console.log(User + "User has logged out");
    }

    return (
        <DropdownMenu>
            <DropdownMenuTrigger asChild>
                    <Button variant="ghost" size="icon">
                        <Link href={"/profile"}>
                            <Image src={userIcon} alt="User icon" width={32} height={32} />
                        </Link>
                    </Button>
            </DropdownMenuTrigger>
            <DropdownMenuContent className="w-56">
                <DropdownMenuLabel>My Account</DropdownMenuLabel>
                <DropdownMenuSeparator />
                <DropdownMenuGroup>
                    <Link href={"/profile"}>
                        <DropdownMenuItem>
                            <User className="mr-2 h-4 w-4" />
                            <span>Profile</span>
                        </DropdownMenuItem>
                    </Link>
                </DropdownMenuGroup>
                <DropdownMenuSeparator />
                <DropdownMenuItem  onClick={handleLogout}>
                    <LogOut className="mr-2 h-4 w-4" />
                    <span>Log out</span>
                </DropdownMenuItem>
            </DropdownMenuContent>
        </DropdownMenu>
    )
}
