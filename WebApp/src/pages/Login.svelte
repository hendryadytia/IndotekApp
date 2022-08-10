<script>
     import {apiurl} from '../helper/apiurl.js'
    import {isLogin} from '../stores/LoginCondition'
     import {Link,useNavigate, useLocation } from "svelte-navigator";
    import Card from '../components/Card.svelte'
    import Input from '../components/inputText.svelte'
    import ButtonSmall from '../components/ButtonSmall.svelte'
    import X from 'jquery'
    import NotificationChanges from "../components/NotificationChanges.svelte"

    const navigate = useNavigate();
    const location = useLocation();

    let uname,pswd
    let triggernotif,bodynotif,headnotif ,responsestatus

    const loginin = () => {
        uname = X("input[name=username]").val()
        pswd = X("input[name=password]").val()

        X.post(apiurl+"login",
                {
                    username:uname,
                    password:pswd,
                },
                function(data, status){
                    if(status == "success")
                    {
                        if(data.code == '200'){
                        document.cookie = "jabatan=" + data.data.jabatan
                        document.cookie = "token=" + data.data.token
                        document.cookie = "username=" + data.data.username
                        isLogin.set("terlogin")
                        navigate("/", {
                            state: { from: $location.pathname },
                            replace: true,
                        })
                        }else if(data.code == '404'){
                            triggernotif = "show"
                        bodynotif = data.message
                        headnotif = "Login"
                        username = password = ''
                        responsestatus = "xx"

                        }
                    }
                });

     
    }


    let display = "block"
  
</script>

<body style="display:{display}; position:absolute">
    <div class="container">
        <div class="kiri" id="kiri">
            <div class="logobox">
                <svg class="logo" xmlns="http://www.w3.org/2000/svg" width="100" height="100" viewBox="0 0 45.603 45.603">
                    <g id="logo_indotek" data-name="logo indotek" transform="translate(0)">
                      <path id="Path_9" data-name="Path 9" d="M21.158,43.617H18.421v.758H17.89v-.758H15.237L9.416,38.28v.466H8.885v-.953L7.344,36.381l.179-.195.179-.194.726.665.457.419V14.82l-.748.013c-.221,0-.429,0-.614,0v-.53c.491,0,.917-.008,1.357-.016,4.731-.087,10.734-.2,12.535,7.8l-.258.058h.264V38.405h-.53V22.175c-.016-.067-.031-.135-.048-.2a9.67,9.67,0,0,0-2.424-4.708V43.086h2.737v.531ZM9.416,37.562l2.471,2.266V14.837c-.836-.044-1.669-.039-2.471-.026V37.562Zm3,2.752,3.024,2.773H17.89v-26.3a7.742,7.742,0,0,0-2.471-1.343V42.456h-.531V15.281a13.409,13.409,0,0,0-2.472-.409Z" transform="translate(-1.302 -2.53)" fill="#094" fill-rule="evenodd"/>
                      <path id="Path_10" data-name="Path 10" d="M31.357,43.617h2.736v.758h.531v-.758h2.653L43.1,38.28v.466h.531v-.953l1.541-1.413-.179-.195-.179-.194-.725.665-.457.419V14.82l.749.013c.22,0,.429,0,.613,0v-.53c-.491,0-.917-.008-1.356-.016-4.732-.087-10.735-.2-12.536,7.8l.258.058h-.266V38.405h.531V22.175c.016-.067.031-.135.048-.2a9.677,9.677,0,0,1,2.424-4.708V43.086H31.357ZM43.1,37.562l-2.471,2.266V14.837c.836-.044,1.669-.039,2.471-.026V37.562Zm-3,2.752-3.024,2.773H34.624v-26.3a7.751,7.751,0,0,1,2.471-1.343V42.456h.531V15.281a13.408,13.408,0,0,1,2.472-.409Z" transform="translate(-5.511 -2.53)" fill="#094" fill-rule="evenodd"/>
                      <path id="Path_11" data-name="Path 11" d="M22.8,0a22.812,22.812,0,0,1,22.25,17.8H38.463A16.441,16.441,0,0,0,6.673,19.619H45.383A22.805,22.805,0,0,1,.551,27.8H7.141a16.442,16.442,0,0,0,31.79-1.821H.22A22.8,22.8,0,0,1,22.8,0" transform="translate(0)" fill="#e60013"/>
                      <path id="Path_12" data-name="Path 12" d="M23.046,10.49V9.517a1.046,1.046,0,0,1,.328-.815,1.178,1.178,0,0,1,.824-.281h5.239a2.753,2.753,0,0,1,.52.044v0a1.053,1.053,0,0,1,.482.212.948.948,0,0,1,.288.564,3.331,3.331,0,0,1,.049.612c0,.335-.009.613-.027.833a1.87,1.87,0,0,1-.123.593.956.956,0,0,1-.368.422,1.294,1.294,0,0,1-.492.179.849.849,0,0,0-.545.272,1.047,1.047,0,0,0-.177.662V35.973a.493.493,0,0,0,.105.308,1.148,1.148,0,0,0,.434.318,3.672,3.672,0,0,1,.513.285,1.215,1.215,0,0,1,.38.388l0,0a1.389,1.389,0,0,1,.144.444,2.978,2.978,0,0,1,.038.494v.7a3.152,3.152,0,0,1-.038.542.782.782,0,0,1-.327.558,1.035,1.035,0,0,1-.421.13,3.864,3.864,0,0,1-.438.022H24.025a1.14,1.14,0,0,1-.717-.215.867.867,0,0,1-.339-.723q0-.655.026-1.094a3.142,3.142,0,0,1,.094-.674,1.016,1.016,0,0,1,.415-.548,1.966,1.966,0,0,1,.606-.271,1.177,1.177,0,0,0,.527-.262.377.377,0,0,0,.1-.276V13.256a3.871,3.871,0,0,0-.038-.595,1.3,1.3,0,0,0-.1-.346.5.5,0,0,0-.148-.17,1.665,1.665,0,0,0-.4-.217,2.975,2.975,0,0,1-.45-.224,1.186,1.186,0,0,1-.365-.327l0,0a1.1,1.1,0,0,1-.159-.429,2.644,2.644,0,0,1-.035-.454Zm1.061-.972v.972a1.709,1.709,0,0,0,.017.276c0,.022-.008.012-.008.016s.012,0,.046.024a1.982,1.982,0,0,0,.293.141,2.683,2.683,0,0,1,.656.371,1.535,1.535,0,0,1,.452.547,2.364,2.364,0,0,1,.183.633,4.893,4.893,0,0,1,.055.76V36.1a1.4,1.4,0,0,1-.41,1.018,2.188,2.188,0,0,1-1,.54.96.96,0,0,0-.282.117s0,0,0-.007l0-.006,0,0a2.535,2.535,0,0,0-.047.427c-.013.223-.02.526-.021.909h5.408l.159,0c0-.055,0-.118,0-.188v-.7a2.061,2.061,0,0,0-.021-.321.4.4,0,0,0-.028-.108.473.473,0,0,1-.055-.03,2.523,2.523,0,0,0-.36-.2,2.2,2.2,0,0,1-.829-.633,1.532,1.532,0,0,1-.324-.95V12.817a2.056,2.056,0,0,1,.413-1.325,1.859,1.859,0,0,1,1.2-.652h0a.393.393,0,0,0,.07-.016c.007-.039.016-.114.025-.222.015-.185.023-.434.023-.745A2.728,2.728,0,0,0,29.7,9.5a2,2,0,0,0-.259-.015H24.2c-.072,0-.109,0-.111,0s.019,0,.019.032Zm5.647-.03v0c.015.011.021.016,0,0Zm0,0s0,0,0,0l0,0ZM24.1,37.76l0,.007,0,.006c0-.01,0-.026,0-.013" transform="translate(-4.071 -1.493)" fill="#e60013"/>
                      <path id="Path_13" data-name="Path 13" d="M26.029,13.485V36.334c0,1.352-1.457,1.509-1.691,1.674l0-.007,0-.006-.07,1.339,5.566,0-.1-1.35c-.431-.3-1.512-.589-1.512-1.78V13.046a1.839,1.839,0,0,1,1.614-1.978.393.393,0,0,0,.07-.016l.029-1.326-5.5-.014-.092.034c0,2.02-.155.884,1,1.8a2.268,2.268,0,0,1,.689,1.94" transform="translate(-4.3 -1.721)" fill="#fff" fill-rule="evenodd"/>
                    </g>
                </svg>
                <h2 class="namapt">CV. ISM</h2>
                <h2 class="slogan">design, supply, manufacture</h2>
            </div>
        </div>
        <div class="kanan" id="kanan">
              
            <Card  useShadow > 
            
                <form>
                    <div>
                        <p style="margin-bottom: 7px;">Username</p>
                        <Input name={"username"} label={"username"} noLabel={true}/>
                    </div>
                    <div>
                        <p style="margin-bottom: 7px;">Password</p>
                        <Input type="password" name={"password"} label={"password"} noLabel={true}/>
                    </div>
                
                    <div style="display: flex; justify-content: right; align-items: center">
                      
                        
                        <ButtonSmall on:click={loginin} style="align-self: flex-end">login</ButtonSmall>
                        
                    </div>
                </form>
            
            </Card>
            <p class="jargon">
                Sistem Pendataan Terpadu <br />
                Untuk Mesin Dalam Perbaikan test
            </p>
        </div>
    </div>

   
</body>


{#if triggernotif == "show"}
<NotificationChanges bind:trigger={triggernotif} body={bodynotif} head={headnotif} status={responsestatus}/>
{/if}

<style>

    form{
        margin: 20px;
        height: 100%;
        display: flex;
        flex-direction: column;
        row-gap: 16px;
    }


body {
  background-color: #c3c3c3;
}

.container {
  display: flex;
}

.kiri {
  width: 40%;
  background-color: #c3c3c3;
  height: 100vh;
}

.kanan {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  width: 65%;
  position: absolute;
  height: 100vh;
  right: 0;
  border-radius: 70px 0 0 70px;
  filter: drop-shadow(-9px 3px 6px rgba(0, 0, 0, 0.161));
  background-color: white;
}



.logobox {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  height: 100%;
  margin-right: 10%;
}


.jargon {
  position: absolute;
  bottom: 0;
  margin: 0 auto;
  margin-bottom: 50px;
  font-size: 29px;
  color: var(--font-color);
  text-align: center;
}

.namapt {
  margin-top: 15px;
  font-weight: 500;
  font-size: 57px;
  color: white;
}

.slogan {
  font-weight: 200;
  font-size: 32px;
  color: white;
}


</style>