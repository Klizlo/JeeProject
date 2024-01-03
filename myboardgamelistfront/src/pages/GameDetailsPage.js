import { Alert, Snackbar, Typography } from "@mui/material";
import { Box } from "@mui/system";
import { useEffect, useState } from "react";
import { useParams } from "react-router";
import GameDetailsComponent from "../components/GameDetailsComponent";

export default function GameDetailsPage() {

    const { id } = useParams();

    const [error, setError] = useState('');
    const [openAlert, setOpenAlert] = useState(false);

    const [data, setData] = useState(null);

    const handleAlert = (e) => {
        setOpenAlert(false);
    };
    const token = localStorage.getItem("token") == null ? sessionStorage.getItem("token") : localStorage.getItem("token");
    const user = JSON.parse(localStorage.getItem("user") == null ? sessionStorage.getItem("user") : localStorage.getItem("user"));

    useEffect(() => {
        fetch(`http://localhost:8080/api/users/${user.id}/boardGames/${id}`, {
            method: "GET",
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json',
                'Authorization': 'Bearer ' + token
            }
        })
            .then((response) => {
                if (response.status === 401) {
                    window.location = '/';
                    return Promise.reject(new Error());
                } else {
                    return response.json();
                }
            })
            .then((result) => {
                console.log(result)
                if(result.id){
                    setData(result);
                } else {
                    setError(`Nie znaleziono gry o id równym ${id}`);
                    setOpenAlert(true);
                }
            }, (error) => { console.log(error)})
    }, []);

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
                Szczegóły gry {data != null ? data.name : ''}
            </Typography>
            {data != null ? (<GameDetailsComponent game={data} />) : (<Typography>Ładowanie...</Typography>)}
            <Snackbar
                anchorOrigin={{ vertical: 'top', horizontal: 'center' }}
                open={openAlert}
                onClose={handleAlert}
                autoHideDuration={6000}
            >
                <Alert severity="error" sx={{ width: '100%' }}>
                    {error}
                </Alert>
            </Snackbar>
        </Box>
    );
}