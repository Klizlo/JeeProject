import { Cancel, Delete } from "@mui/icons-material";
import Edit from "@mui/icons-material/Edit";
import { Alert, Box, Button, Dialog, DialogActions, DialogTitle, Grid, Snackbar, Typography } from "@mui/material";
import { useState } from "react";

export default function GameDetailsComponent(game) {

    const [data, setData] = useState(game.game);

    const [open, setOpen] = useState(false);
    const [error, setError] = useState("");
    const [openAlert, setOpenAlert] = useState(false);

    const handleAlertClose = () => {
        setOpenAlert(false);
    }

    const handleClick = () => {
        setOpen(false);
        fetch('http://localhost:8080/api/boardGames/' + data.id, {
            method: "DELETE",
            headers: { 
                'Content-Type': 'application/json',
                'Accept': 'application/json',
                'Authorization': 'Bearer ' + (localStorage.getItem("token") == null ? sessionStorage.getItem("token") : localStorage.getItem("token"))
            },
        }).then((response) => {
            if (response.ok) {
                window.location = '/'
            } else if (response.status === 401) {
                setOpenAlert(true);
                setError("Nieautoryzowane działanie");
                return Promise.reject(new Error());
            } else {
                return response.json()
            }
        })
            .then((result) => {
                setOpenAlert(true);
                setError("Nie znaleziono gry");
            }, (error) => console.log(error))
    }

    return (<Box>
        <Grid container spacing={{ xs: 2, md: 3 }}>
            <Grid item xs={12} sm={12} md={6}>
                <Box component='img' src={data.picture != null ? data.picture : 'logo.png'} sx={{ width: '70%', maxWidth: '450px'}} />
            </Grid>
            <Grid item xs={12} sm={12} md={6} >
                <Typography sx={{
                padding: '2%'
            }}>
                    <b>Developer:</b> {data.developer}
                </Typography>
                <Typography sx={{
                padding: '2%'
            }}>
                    <b>Minimalna liczba graczy:</b> {data.minNumberOfPlayers}
                </Typography>
                <Typography sx={{
                padding: '2%'
            }}>
                    <b>Maksymalna liczba graczy:</b> {data.maxNumberOfPlayers}
                </Typography>
                <Typography sx={{
                padding: '2%'
            }}>
                    <b>Liczba rozegranych godzin:</b> {data.numberOfHours}
                </Typography>
                <Typography sx={{
                padding: '2%'
            }}>
                    <b>Kategoria:</b> {data.category.name}
                </Typography>
            </Grid>
            <Grid item xs={12} sm={12} md={6}>
            <Typography sx={{
                padding: '2%'
            }}>
                    <b>Opis:</b>
                </Typography>
            </Grid>
            <Grid item xs={12} sm={12} md={6} >
                <Typography sx={{
                padding: '2%'
            }}>
                    {data.description == null ? 'Brak opisu' : data.description}
                </Typography>
            </Grid>
            <Grid item xs={12} sm={12} md={12} >
                <Button
                    sx={{
                        background: 'orange',
                        color: 'white',
                        fontWeight: 700,
                        '&:hover': {
                            color: 'black'
                        },
                        margin: '2%'
                    }}
                    startIcon={<Edit />}
                    onClick={() => {
                        window.location = '/games/' + data.id + '/update'
                      }}>
                    Zaktualizuj
                </Button>
                <Button
                    sx={{
                        background: 'red',
                        color: 'white',
                        fontWeight: 700,
                        '&:hover': {
                            color: 'black'
                        }
                    }}
                    startIcon={<Delete />}
                    onClick={() => {
                        setOpen(true);
                      }}>
                    Usuń
                </Button>
            </Grid>
        </Grid>
        <Dialog
            open={open}
            onClose={() => setOpen(false)}
            aria-labelledby="draggable-dialog-title"
        >
            <DialogTitle style={{ cursor: 'move' }} id="draggable-dialog-title">
            Czy chcesz usunąć grę z listy?
            </DialogTitle>
            <DialogActions>
            <Button startIcon={<Cancel />} autoFocus onClick={() => {setOpen(false)}}>
                Anuluj
            </Button>
            <Button sx={{color: 'red'}} startIcon={<Delete />} onClick={handleClick}>Usuń</Button>
            </DialogActions>
        </Dialog>
        <Snackbar
            anchorOrigin={{ vertical: 'top', horizontal: 'center' }}
            open={openAlert}
            onClose={handleAlertClose}
            autoHideDuration={6000}
        >
            <Alert severity="error" sx={{ width: '100%' }}>
                {error}
            </Alert>
        </Snackbar>
    </Box>);
}