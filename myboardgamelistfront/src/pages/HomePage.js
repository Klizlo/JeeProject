import { Box, Typography } from "@mui/material";
import { useEffect, useState } from "react";
import GameTable from "../components/GameTable";

export default function HomePage() {

    const [games, setGames] = useState([]);

    useEffect(()=> {
        const token = localStorage.getItem("token") == null ? sessionStorage.getItem("token") : localStorage.getItem("token");
        const user = JSON.parse(localStorage.getItem("user") == null ? sessionStorage.getItem("user") : localStorage.getItem("user"));
        console.log(user);
        fetch("http://localhost:8080/api/users/" + user.id + "/boardGames", {
            method: "GET",
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json',
                'Authorization': 'Bearer ' + token
            }
        })
        .then((response) => response.json())
        .then((result) => {
            setGames(result);
        })
    }, [])

    return (
        <Box
        sx={{
            margin: '2%'
        }}>
            <Typography
                variant="h5"
                sx={{
                    fontFamily: 'kdam-thmor-pro',
                    fontWeight: 700,
                    letterSpacing: '.3rem',
                    color: '#fe2800'
                }}
            >
                Lista gier
            </Typography>
            <GameTable data={games}/>
        </Box>
    );
}