<script>
     import {apiurl} from '../helper/apiurl.js'
    import DataTables from 'datatables'
    import Card from '../components/Card.svelte'    
    import ButtonSmall from '../components/ButtonSmall.svelte'
    import Input from '../components/inputText.svelte'
    import DataList from '../components/DataListForCard.svelte'
    import NotificationChanges from './NotificationChanges.svelte'
    import jqueryDataTables from 'datatables';
    import X from 'jquery'
    import { onMount, tick } from 'svelte'
    export let lihatDetail = ()=>{}
    export let editDetail = ()=>{}
    export let tableHead 
    export let dataTableCFG 
    export let width = 600
    export let id,name,alamat,notelp,orkvisibility
    export let context,daridafpel
    let intervalrefresh
    
    let pemilik
    let inputPassword
    let tabe 
    let table
    let triggernotif,responsestatus
    let bodynotif
    let headnotif
    let deleteMesin = apiurl+"hapusmesin"
    let deleteTeknisi = apiurl+"teknisihapus"
    let deletePelanggan = apiurl+"pelangganhapus"
    let deleteAdmin = apiurl+"adminhapus"
    onMount(()=> {
        DataTables() 
        table = X(tabe).DataTable(
            dataTableCFG
        )
        intervalrefresh = setInterval( function () {
            table.ajax.reload(null,false);
        }, 5000 );
        X('tbody').before("\u200C")
        X(document).on('click', function(e){
            if(e.target.tagName !== "IMG"){
                X('.cxmenu').css("display","none")
            }
        })
        
        X('.hapus').on('click', function(){
             X('.overlay-confirm').css('display','flex')
        })
      
        X('.overlay-confirm').on('click', function(e){
            if(e.target == e.currentTarget) {
                X('.overlay-confirm').css('display','none')

                console.log(X('#password-for-delete').val()); 
                X('#password-for-delete').val('')
            }
        })

        //kalau tombol delete sudah di klik = 1
        let isclicked = 0;
        
        //tombol konfirmasi delete
        X('.confirm-delete').on('click',function(){
           
            X.post(apiurl+"login",
                {
                    username:getCookie("username"),
                    password:inputPassword,
                },
                function(data, status){
                    if(status == "success")
                    {
                        if(data.code == '200'){ 
                            X('.overlay-confirm').css('display','none')
                          


                            if(context === "Mesin"){

                              

                                    X.ajax({
                                        url:deleteMesin,
                                        type:"POST",
                                        data: {
                                            id: id
                                        },
                                        
                                        beforeSend: function (request) {
                                                        request.setRequestHeader('Authorization', getCookie('token'));
                                                    },
                                        success: function(result,status){
                                            if(status == "success")
                                                {
                                                    headnotif = "Delete"
                                                    bodynotif = "berhasil delete data Mesin" + name
                                                    triggernotif = "show"
                                                    responsestatus = "sukses"
                                                    
                                                }
                                                inputPassword = ""
                                        }
                                                    
                                        },
                                    )
                                }else if(context === "Teknisi"){


                                    X.ajax({
                                        url:deleteTeknisi,
                                        type:"POST",
                                        data: {
                                            id: id
                                        },
                                        
                                        beforeSend: function (request) {
                                                        request.setRequestHeader('Authorization', getCookie('token'));
                                                    },
                                        success: function(result,status){
                                            if(status == "success")
                                                {
                                                    headnotif = "Delete"
                                                    bodynotif = "berhasil delete data Teknisi " + name
                                                    triggernotif = "show"
                                                    responsestatus = "sukses"

                                                }
                                                inputPassword = ""
                                        }
                                                    
                                        },
                                    )
                                }else if(context === "Customer"){
                                X.ajax({
                                        url:deletePelanggan,
                                        type:"POST",
                                        data: {
                                            id: id
                                        },
                                        
                                        beforeSend: function (request) {
                                                        request.setRequestHeader('Authorization', getCookie('token'));
                                                    },
                                        success: function(result,status){
                                            if(status == "success")
                                                {
                                                    headnotif = "Delete"
                                                    bodynotif = "berhasil delete data Pelanggan" + name
                                                    triggernotif = "show"
                                                    responsestatus = "sukses"

                                                }
                                                inputPassword = ""
                                        }
                                                    
                                        },
                                    )
                                }
                                else if(context === "Admin"){
                                X.ajax({
                                        url:deleteAdmin,
                                        type:"POST",
                                        data: {
                                            id: id
                                        },
                                        
                                        beforeSend: function (request) {
                                                        request.setRequestHeader('Authorization', getCookie('token'));
                                                    },
                                        success: function(result,status){
                                            if(status == "success")
                                                {
                                                    headnotif = "Delete"
                                                    bodynotif = "berhasil delete data Admin" + name
                                                    triggernotif = "show"
                                                    responsestatus = "sukses"

                                                }
                                                inputPassword = ""
                                        }
                                                    
                                        },
                                    )
                                }

                        }else if(data.code == '404'){
                            triggernotif = "show"
                        bodynotif = data.message
                        headnotif = "Login"
                        username = inputPassword = ''
                        }
                    }
                });
            //ambil password yg diinput user
            
     


            
            
        })

       
    X('tbody').on( 'click', '.cxbtn', function (e) {
            // get data dari datatable
            var data = table.row( X(this).parents('tr') ).data();
            // alert("okay") 
            //simpan id dan nama
            if(context== "Mesin"){
                  id = data.id
                  name = data.jenis_mesin
                  pemilik = data.nama_pemilik

            }else if(context == "Teknisi"){
                    id = data.id
                    name = data.nama
                  pemilik = data.alamat
            }else if(context == "Admin"){
                id = data.id
                    name = data.nama
            }


            // buka context menu 
            var pageX = e.pageX;
            var pageY = e.pageY;
            X('.cxmenu').css("left",pageX + "px")
            X('.cxmenu').css("top",pageY + "px")
            X('.cxmenu').css("display","block")
        } 

        
    );
    if(context == 'Customer' ){
        X('tbody').on( 'click','tr', function (e) {
            // get data dari datatable
            var data = table.row( X(this) ).data();
          
            // alert("okay") 
            //simpan id dan nama
            id = data.id
            name = data.nama
            pemilik = alamat = data.alamat
            notelp = data.no_telp
            daridafpel = "daftarpel"
            orkvisibility = false
        } 

        
    );
    }
    


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


     return () => clearInterval(intervalrefresh)
    })


  
     
</script>


<style global> 

   
    thead{
        background-color: #D8D4D4;
        color: #A71818;
        box-shadow: -1px 3px 10px 3px rgb(200 200 200 );  
        
    }
    table.dataTable thead th, table.dataTable thead td {
        padding: 10px 18px;
        border-bottom:none;
        color: var(--accent-color);
    }
    a{

        text-decoration: none;
    }

    i{
        color:var(--accent-color);
        font-weight: bold;
    }

    .dataTables_filter{
        margin-bottom: 10px;
    }

    tbody:before{
        line-height: 1em;
        content: "\200C";
        display: block;
    }
</style>

<!-- data tables -->
<div style="width: {width}px;">
    <table bind:this={tabe} class="display">
        <thead>
            <tr>
                {#each tableHead as tabhead}
                <th>{tabhead}</th>
                {/each}
            </tr>
        </thead>
    </table>
</div>

<!-- datatables context menu -->
{#if context != "Loglogin" }
<Card style="display: none; position: absolute;" useShadow Class="cxmenu">
    <ul style="display: flex; flex-direction: column; gap: 8px; ">
        {#if context != "Admin"}
            <li>
                <a  href="#" class="lihat" on:click={lihatDetail}>
                    <i>Lihat</i>
                </a>
            </li>
        {/if}

        <li>
            <a href="#" class="edit" on:click={editDetail}>
                <i>Edit</i>
            </a>
        </li>
        <li>
            <a href="#" class="hapus">
                <i>Hapus</i>
            </a>
        </li>
    </ul>
    </Card>
{/if}
<!-- overlay delete confirmation -->
    <div style="height: 100vh; width: 100vw; position: absolute; top:0; left:0;background-color: rgba(0, 0, 0, 0.363); display: none; align-items: center; justify-content: center; z-index: 30" class="overlay-confirm">
        <Card width=300 useShadow style="position:relative">
            <div style="display: flex; flex-direction: column; justify-content:flex-end; height: inherit; justifiy-content: flex-end; gap: 20px"> 
                <Card width=300 useShadow style="margin-top: -10px;margin-left:-10px; top:0; position: absolute">
                    <h1 style="margin-bottom: auto; color: var(--accent-color)">Konfirmasi Hapus Data</h1>
                </Card>

                <DataList style="margin-top: 40px" title='ID {context}' isi={id}/>
                <DataList title={context} isi={name}/>
                {#if context === 'Mesin'}
                <DataList title='Pemilik' isi={pemilik}/>
                {:else}
                    {#if context != 'Admin'}
                    <DataList title='Alamat' isi={pemilik}/>
                    {/if}
                {/if}

               
                <Input type="password" id="password-for-delete" bind:value={inputPassword} placeholder="Ketikkan Password Administrasi" noLabel width='100%'/>
                <ButtonSmall class="confirm-delete"> Delete</ButtonSmall>
            </div>
        </Card>
        
    </div>
    

    {#if triggernotif == "show"}
        <NotificationChanges bind:trigger={triggernotif} body={bodynotif} head={headnotif} status={responsestatus}/>
    {/if}