import { Alert, Button, Grid, MenuItem, Snackbar, TextField, Typography } from "@mui/material";
import { Box } from "@mui/system";
import { useEffect, useState } from "react";
import SaveIcon from '@mui/icons-material/Save';
import LoadingButton from '@mui/lab/LoadingButton';
import { Cancel } from "@mui/icons-material";
import {redirect} from "react-router";

export default function EditGameForm({game}) {

    const [categories, setCategories] = useState([]);
    
    const [data, setData] = useState({
        name: game.name,
        developer: game.developer,
        minNumberOfPlayers: game.minNumberOfPlayers,
        maxNumberOfPlayers: game.maxNumberOfPlayers,
        numberOfHours: game.numberOfHours,
        category: game.category.id,
        picture: game.picture != null ? game.picture : '',
        description: game.description != null ? game.description : ''
    });

    const [loading, setLoading] = useState(false);
    const [openAlert, setOpenAlert] = useState(false);
    const [error, setError] = useState("");

    const handleSubmit = (e) => {
        e.preventDefault();
        setLoading(true);
        if(validate){
            fetch('http://localhost:8080/api/boardGames/' + game.id,{
                method: "PUT",
                headers: {
                    'Content-Type': 'application/json',
                    'Accept': 'application/json',
                    'Authorization': 'Bearer ' + (localStorage.getItem("token") == null ? sessionStorage.getItem("token") : localStorage.getItem("token"))
                },
                body: JSON.stringify({
                    name: data.name,
                    developer: data.developer,
                    minNumberOfPlayers: data.minNumberOfPlayers,
                    maxNumberOfPlayers: data.maxNumberOfPlayers,
                    numberOfHours: data.numberOfHours,
                    category: {
                        id: data.category
                    },
                    picture: data.picture
                })
            })
                .then((response) => {
                    if (response.status === 401) {
                        window.location = '/';
                    } else {
                        return response.json();
                    }
                })
                .then((result) => {
                    console.log(result)
                    if(result.id){
                        setData(result);
                        window.location = '/';
                    } else {
                        setError(result.msg);
                        setOpenAlert(true);
                    }
                })
        } else {
            setOpenAlert(true);
        }
    };

    const handleChange = (e) => {
        console.log(e.target.value);
        setData({...data, [e.target.name]: e.target.value});
    };

    const handleAlert = (e) => {
        setOpenAlert(false);
    };

    const validate = () =>{
        if(data.name.length > 255){
            setError('Nazwa gry nie moze przekraczać 255 znaków');
            return false;
        }
        if(data.developer.length > 255){
            setError('Nazwa twórcy nie moze przekraczać 255 znaków');
            return false;
        }
        if(data.minNumberOfPlayers > data.maxNumberOfPlayers){
            setError('Minimalna liczba graczy nie może być większa niż maksymalna liczba graczy');
            return false;
        }
        return true;
    };

    useEffect(() => {
        fetch('http://localhost:8080/api/categories', {
            method: "GET",
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json'
            }
        })
        .then((response) => response.json())
        .then((result) => {
            setCategories(result);
        })
    }, []);
    
    if(categories){
        return (
            <Box component='div'>
                <Box component="form" method="POST" onSubmit={handleSubmit} sx={{ mt: 1 }}>
                    <Grid container spacing={{ xs: 2, md: 3 }}>
                        <Grid item xs={12} sm={12} md={6}>
                            <TextField
                                margin="normal"
                                inputProps={{
                                    maxLength: 255,
                                }}
                                required
                                fullWidth
                                id="name"
                                onChange={handleChange}
                                label="Tytuł gry"
                                name="name"
                                value={data.name}
                            />
                        </Grid>
                        <Grid item xs={12} sm={12} md={6}>
                            <TextField
                                margin="normal"
                                inputProps={{
                                    maxLength: 255,
                                }}
                                required
                                fullWidth
                                id="developer"
                                onChange={handleChange}
                                label="Twórca"
                                name="developer"
                                value={data.developer}
                            />
                        </Grid>
                        <Grid item xs={12} sm={12} md={6}>
                            <TextField
                                margin="normal"
                                type="number"
                                required
                                fullWidth
                                id="minNumberOfPlayers"
                                onChange={handleChange}
                                label="Minimalna liczba graczy"
                                inputProps={{
                                    min: '0'
                                }}
                                name="minNumberOfPlayers"
                                value={data.minNumberOfPlayers}
                            />
                        </Grid>
                        <Grid item xs={12} sm={12} md={6}>
                            <TextField
                                margin="normal"
                                type="number"
                                required
                                fullWidth
                                id="maxNumberOfPlayers"
                                onChange={handleChange}
                                label="Maksymalna liczba graczy"
                                name="maxNumberOfPlayers"
                                value={data.maxNumberOfPlayers}
                                inputProps={{
                                    min: '0'
                                }}
                            />
                        </Grid>
                        <Grid item xs={12} sm={12} md={6}>
                            <TextField
                                margin="normal"
                                type="number"
                                fullWidth
                                id="numberOfHours"
                                onChange={handleChange}
                                label="Liczba rozegranych godzin"
                                name="numberOfHours"
                                value={data.numberOfHours}
                                inputProps={{
                                    min: '0',
                                    step: "0.01"
                                }}
                            />
                        </Grid>
                        <Grid item xs={12} sm={12} md={6}>
                            <TextField
                                margin="normal"
                                select
                                required
                                fullWidth
                                id="category"
                                onChange={handleChange}
                                label="Kategoria"
                                name="category"
                                value={data.category}
                            >
                                {categories.map((category) => (
                                    <MenuItem key={category.id} value={category.id}>
                                        {category.name}
                                    </MenuItem>
                                ))}
                            </TextField>
                        </Grid>
                        <Grid item xs={12} sm={12} md={6}>
                            <TextField
                                margin="normal"
                                fullWidth
                                id="picture"
                                onChange={handleChange}
                                label="Adres obrazku"
                                name="picture"
                                value={data.picture}
                            />
                        </Grid>
                        <Grid item xs={12} sm={12} md={6}>
                            <Box component="img" src={data.picture !== '' ? data.picture : 'logo.png'} 
                            sx={{
                                width: '200px'
                            }} />
                        </Grid>
                        <Grid item xs={12} sm={12} md={12}>
                            <TextField
                                label="Opis"
                                multiline
                                id="description"
                                name="description"
                                onChange={handleChange}
                                rows={4}
                                fullWidth
                                value={data.description}
                            />
                        </Grid>
                    </Grid>
                    <LoadingButton
                        type="submit"
                        loading={loading}
                        loadingPosition="start"
                        startIcon={<SaveIcon />}
                        variant="contained"
                        sx={{ 
                            mt: 3,
                            mb: 2,
                            background: 'green',
                            '&:hover': {
                                backgroundColor: 'black'
                            }
                        }}
                    >
                        Zapisz
                    </LoadingButton>
                    <Button
                        startIcon={<Cancel />}
                        variant="contained"
                        sx={{ 
                            mt: 3,
                            mb: 2,
                            background: 'orange',
                            '&:hover': {
                                background: 'white',
                                color: 'black'
                            }
                        }}
                        onClick={()=>{
                            window.location = '/';
                        }}>
                            Anuluj
                    </Button>
                </Box>
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
}