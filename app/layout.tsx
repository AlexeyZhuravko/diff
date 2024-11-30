import type { Metadata } from "next";
import localFont from "next/font/local";
import {Poppins} from "next/font/google";
import { ClerkProvider,
  SignInButton,
  SignedIn,
  SignedOut,
  UserButton} from "@clerk/nextjs";

import "./globals.css";

const poppins = Poppins({
  subsets: ["latin"],
  weight: ["400", "500", "600", "700"],
  variable: "--font-poppins",
});

const geistSans = localFont({
  src: "./fonts/GeistVF.woff",
  variable: "--font-geist-sans",
  weight: "100 900",
});
const geistMono = localFont({
  src: "./fonts/GeistMonoVF.woff",
  variable: "--font-geist-mono",
  weight: "100 900",
});

export const metadata: Metadata = {
  title: "Evently",
  description: "Evently is a platform for organizing events and activities.",
  icons: {
    icon: '/assets/images/logo.svg',
  }
};

export default function RootLayout({
  children,
}:{
  children: React.ReactNode;
}) {
  return (
    
    <ClerkProvider>
    <html lang="en">
      <body className={poppins.variable}>        
        {children}
      </body>
    </html>
  </ClerkProvider>
  );
}

