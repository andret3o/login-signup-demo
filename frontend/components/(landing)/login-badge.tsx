"use client";

import { Badge } from "../ui/badge";
import { useAuth } from "@/context/AuthContext";

export function LoginBadge() {
  const { isLoggedIn, loading } = useAuth();

  return isLoggedIn ? (
    <Badge className="bg-green-50 text-green-700 dark:bg-green-950 dark:text-green-300">
      Logged in
    </Badge>
  ) : (
    <Badge variant="destructive">Logged out</Badge>
  );
}
