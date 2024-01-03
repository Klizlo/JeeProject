import { LockOutlined } from "@mui/icons-material";
import { Avatar, Box, Container, Grid, Paper,
     Typography, TextField, FormControlLabel,
    Checkbox} from "@mui/material";
import Link from '@mui/material/Link';
import Alert from '@mui/material/Alert';
import LoadingButton from '@mui/lab/LoadingButton';
import { useState } from "react";
import SaveIcon from '@mui/icons-material/Send';

export default function LoginPage() {

    const [data, setData] = useState({email: "", password: ""});
    const [checked, setChecked] = useState(false);
    const [error, setError] = useState("");
    const [loading, setLoading] = useState(false);

    const handleChange = (e) => {
        setData({...data, [e.target.name]: e.target.value});
    };

    const handleChecked = (e) => {
        setChecked(e.target.checked);
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setLoading(true);
        fetch ("http://localhost:8080/auth/signin", {
            method: "POST",
            headers : { 
                'Content-Type': 'application/json',
                'Accept': 'application/json'
            },        
            body: JSON.stringify({
                email: data.email,
                password: data.password
            })
        })
        .then((response) => {
            if (response.ok) {
                return response.json()
            } else {
                return JSON.parse("{\"msg\": \"Poadny użytkownik nie istnieje\"}");
            }
        })
        .then((result) => {
            console.log(result);
            if(result.msg) {
                setError(result.msg);
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
                    backgroundImage: 'url(https://images82.fotosik.pl/823/06f08c7546399cb2.png)',
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
                            <LockOutlined />
                        </Avatar>
                        <Typography
                            variant="h5"
                            sx={{
                                fontFamily: 'kdam-thmor-pro',
                                fontWeight: 700,
                                letterSpacing: '.3rem',
                            }}
                        >
                            Zaloguj się
                        </Typography>
                        <Box component="form" method="POST" onSubmit={handleSubmit} sx={{ mt: 1 }}>
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
                                autoFocus
                            />
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
                                Zaloguj się
                            </LoadingButton>
                            <Grid container>
                                <Grid item>
                                <Link href="/signup" variant="body2" color='#fe2800'>
                                    {"Nie masz jeszcze konta? Zarejestruj się"}
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