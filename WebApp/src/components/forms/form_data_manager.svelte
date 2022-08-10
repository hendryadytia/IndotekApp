<script>
    import {apiurl} from '../../helper/apiurl.js'
    import Card from "../Card.svelte"
    import ButtonSmall from "../ButtonSmall.svelte"
    import InputText from "../inputText.svelte"
    import NotificationChanges from "../NotificationChanges.svelte"
    import X from 'jquery'
    export let formID
    export let formCount
    export let purpose = 'edit'
    let triggernotif,bodynotif,headnotif,responsestatus
    export let buttonTambah = () => {
     
        X.ajax({
            url:apiurl+"updatemanpsw",
            type:"POST",
            data: {
                id:id,
                    nama:namaman,
                    password:pswman,
                    username:username
            },
            
            beforeSend: function (request) {
                            request.setRequestHeader('Authorization', getCookie('token'));
                        },
            success: function(result,status){
                if(status == "success")
                    {
                        bodynotif = "berhasil update data manager"
                        triggernotif = "show"
                        responsestatus = "sukses"
                        formCount--
                        setTimeout(function (){
                                removeMe()    
                            }, 2000);
                    }

            }
                        
            },
        )
            
            }

    
    export let withRemoveButton = true
    let pemilikSamaClass = "sama"
    let namaman,pswman,username,id
    export let removeMe = ()  => {
        formCount--
        // nodeRef.par  entNode.removeChild(nodeRef);
    }
 

    X.ajax({
            url:apiurl+"managername",
            type:"GET",
            beforeSend: function (request) {
                            request.setRequestHeader('Authorization', getCookie('token'));
                        },
            success: function(result){
                namaman = result.data.nama
                username = result.data.username
                id = result.data.id
                console.log(result);
            }
                        
            },
            )
            function getCookie(cname){
        let name = cname + "=";
        let ca = document.cookie.split(';');
        for(let i = 0;i< ca.length; i++){
            let c = ca[i]
            while (c.charAt(0) == ' '){
                c = c.substring(1)
            }
            if(c.indexOf(name) == 0){
                return c.substring(name.length,c.length)
            }
        }
        return ""
    }

</script>



<div id="form_{formID}" class="dataGroup {pemilikSamaClass}" style="display: flex; flex-direction:column; gap:20px;position: relative;">
    {#if withRemoveButton}
    <img src="./icons/plus_button.png" alt="delete" style="transform:rotate(45deg); width: 30px; height: 30px; margin-top: -50px; position: absolute; right: 0" on:click={removeMe}/>
    {/if}
    <!-- data teknisi -->
    <Card width="365" useShadow>
        <div style="display: flex; justify-content: space-between">
            <p style="color: var(--accent-color); font-weight:bold; font-size: 18px">Data Manager</p>
        </div>
        <form name="datateknisi">
            <InputText style="margin-top:16px" label="Nama" name="nama" bind:value={namaman}/>
            <InputText style="margin-top:16px" label="Username" name="username" bind:value={username}/>
            <InputText style="margin-top:16px" label="Password" name="password" bind:value={pswman}/>
            
            <ButtonSmall style="width: 100%; margin-top: 10px" on:click={buttonTambah}>
                <p style="font-size: 20px;
                    color: white; font-weight: normal">
                    {#if purpose === 'tambah'}
                    Tambahkan
                    {:else if purpose === 'edit'}
                    Simpan
                    {/if}
                </p> 
            </ButtonSmall>
        </form>
    </Card>
</div>


{#if triggernotif == "show"}
<NotificationChanges bind:trigger={triggernotif} body={bodynotif} head={headnotif} status={responsestatus}/>
{/if}