import AccountCircleIcon from '@mui/icons-material/AccountCircle';
import { LoadingButton } from '@mui/lab';
import { Alert, Avatar, Box, Checkbox, Container, FormControlLabel, Grid, Link, Paper, TextField, Typography } from "@mui/material";
import { useState } from "react";
import SaveIcon from '@mui/icons-material/Send';


export default function RegisterPage() {

    const [data, setData] = useState({
        name: "",
        email: "",
        password: "",
        password_confirmation: ""
    });
    const [error, setError] = useState("");
    const [emailError, setEmailError] = useState("");
    const [checked, setChecked] = useState(false);
    const [loading, setLoading] = useState(false);

    const handleChange = (e) => {
        setData({...data, [e.target.name]: e.target.value});
    };

    const handleChecked = (e) => {
        setChecked(e.target.checked);
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        setLoading(true);
        if(data.password !== data.password_confirmation){
            setError("Hasła musza byc takie same.");
            setLoading(false);
        } else {
            fetch ("http://localhost:8080/auth/signup", {
                method: "POST",
                headers : { 
                    'Content-Type': 'application/json',
                    'Accept': 'application/json'
                },        
                body: JSON.stringify({
                    name: data.name,
                    email: data.email,
                    password: data.password
                })
            })
            .then((response) => response.json())
            .then((result) => {
                console.log(result);
                if(result.msg) {
                    setError("Podany adress email już jest zajęty");
                    setLoading(false);
                } else if (result.errors) {
                    console.log(result.errors);
                    if(result.errors.email) {
                        setEmailError(result.errors.email)
                    }
                    if (result.errors.password) {
                        let text  = "";
                        if (result.errors.password.includes("more")) {
                            text += "Hasło musi zawierać co najmniej 8 znaków\n"
                        }
                        if (result.errors.password.includes("upper")) {
                            text += "Hasło musi zawierać co najmniej 1 wielką literę\n"
                        }
                        if (result.errors.password.includes("lower")) {
                            text += "Hasło musi zawierać co najmniej 1 małą literę\n"
                        }
                        if (result.errors.password.includes("digit")) {
                            text += "Hasło musi zawierać co najmniej 1 cyfrę\n"
                        }
                        if (result.errors.password.includes("special")) {
                            text += "Hasło musi zawierać co najmniej 1 znak specjalny\n"
                        }
                        setError(text)
                    }
                    setLoading(false);
                } else {
                    if(checked){
                        localStorage.setItem("token", result.token);
                        localStorage.setItem("user", JSON.stringify(result.user));
                    } else {
                        sessionStorage.setItem("token", result.token);
                        sessionStorage.setItem("user", JSON.stringify(result.user));
                    }
                    window.location="/";
                }
            });
        }
    };

    return (
        <Container>
            <Grid container component="main" sx={{ height: '90vh', padding: '2% 0' }}>
                <Grid 
                item
                xs={false}
                sm={4}
                md={7}
                sx={{
                    backgroundImage: 'url(https://munchkin.game/site-munchkin/assets/files/3637/munchkins-and-mazes-cards.png)',
                    backgroundRepeat: 'no-repeat',
                    backgroundColor: (t) =>
                    t.palette.mode === 'light' ? t.palette.grey[50] : t.palette.grey[900],
                    backgroundSize: 'cover',
                    backgroundPosition: 'center',
                }}/>
                <Grid item xs={12} sm={8} md={5} component={Paper} elevation={6} square>
                    <Box
                        sx={{
                            my: 8,
                            mx: 4,
                            display: 'flex',
                            flexDirection: 'column',
                            alignItems: 'center',
                          }}>
                        <Avatar sx={{ m: 1, bgcolor: '#fe2800' }}>
                            <AccountCircleIcon />
                        </Avatar>
                        <Typography
                            variant="h5"
                            sx={{
                                fontFamily: 'kdam-thmor-pro',
                                fontWeight: 700,
                                letterSpacing: '.3rem',
                            }}
                        >
                            Zarejestruj się
                        </Typography>
                        <Box component="form" method="POST" onSubmit={handleSubmit} sx={{ mt: 1 }}>
                            <TextField
                                margin="normal"
                                inputProps={{
                                    maxLength: 255,
                                  }}
                                required
                                fullWidth
                                id="name"
                                onChange={handleChange}
                                label="Nazwa użytkownika"
                                name="name"
                                value={data.name}
                                autoFocus
                            />
                            <TextField
                                margin="normal"
                                required
                                fullWidth
                                id="email"
                                onChange={handleChange}
                                label="Adres email"
                                name="email"
                                value={data.email}
                                autoComplete="email"
                            />
                            {emailError && <Alert severity="error" sx={{ whiteSpace: 'pre-line' }}>{emailError}</Alert>}
                            <TextField
                                margin="normal"
                                required
                                fullWidth
                                name="password"
                                onChange={handleChange}
                                value={data.password}
                                label="Hasło"
                                type="password"
                                id="password"
                                autoComplete="current-password"
                            />
                            <TextField
                                margin="normal"
                                required
                                fullWidth
                                name="password_confirmation"
                                onChange={handleChange}
                                value={data.password_confirmation}
                                label="Powtórz hasło"
                                type="password"
                                id="password_confirmation"
                                autoComplete="current-password"
                            />
                            {error && <Alert severity="error">{error}</Alert>}
                            <FormControlLabel
                                control={<Checkbox value="remember" sx={{
                                    color: '#fe2800',
                                    '&.Mui-checked': {
                                        color: '#fe2800',
                                      }
                                }}
                                    onChange={handleChecked}/>}
                                label="Zapamiętaj mnie"
                            />
                            <LoadingButton
                                type="submit"
                                fullWidth
                                loading={loading}
                                loadingPosition="start"
                                startIcon={<SaveIcon />}
                                variant="contained"
                                sx={{ 
                                    mt: 3,
                                    mb: 2,
                                    background: '#fe2800',
                                    '&:hover': {
                                        backgroundColor: 'black'
                                    }
                                 }}
                            >
                                Zajerestuj się
                            </LoadingButton>
                            <Grid container>
                                <Grid item>
                                <Link href="/login" variant="body2" color='#fe2800'>
                                    {"Masz już założone konto? Zaloguj się"}
                                </Link>
                                </Grid>
                            </Grid>
                        </Box>
                    </Box>
                </Grid>
            </Grid>
        </Container>
    );
}