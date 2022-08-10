<script>
     import {apiurl} from '../helper/apiurl.js'

    import ButtonSmall from "../components/ButtonSmall.svelte"
    import Topbar from "../components/Topbar.svelte";
    import FormDataMesin from "../components/forms/form_data_mesin.svelte";
    import NotificationChanges from '../components/NotificationChanges.svelte'

   
    import X from 'jquery'

    let formCount = 1

    function addForms(){
        formCount++
    }

    const zeroPad = (num, places) => String(num).padStart(places,'0')

    let berhasilGetId = false

    function getIdPemilik(muteifsecond){
        X.ajax({
            url:apiurl+"generatepelangganid",
            type:"GET",
            
            beforeSend: function (request) {
                            request.setRequestHeader('Authorization', getCookie('token'));
                        },
            success: function(result){
                if(!muteifsecond){
                    triggernotif = "show"
                    bodynotif = "berhasil terkoneksi, silahkan kirim data mesin"
                    headnotif = "tambah mesin"
                    responsestatus = "sukses"
                }
                
                berhasilGetId = true
                idSebelumnya =  result.id
           
            }
                        
            },
        )
    }
    getIdPemilik(false)
    
    function today(){
        let dt, mo, yr,today
        today = new Date()
        dt = today.getDate();
        mo = today.getMonth()+1;
        yr = today.getFullYear();

        dt = dt.toString().padStart(2,'0');
        mo = mo.toString().padStart(2,'0');
        yr = yr.toString().substr(-2)

        return dt+mo+yr
    }


    
    let profile = []
    let triggernotif,bodynotif,headnotif,responsestatus
    let idSebelumnya
    let dataPemilik = [] // cikal bakal yg bakal dilooping untuk send ke api
    let dataMesin = [] // cikal bakal yg bakal dilooping untuk send ke api
    let urutanbr
    let idBaru

    let adaygkosong 
    function generateNewPGID(idSebelumnya){
            urutanbr =  parseInt(idSebelumnya.substr(-3))
            idBaru = "PG-"+today()+zeroPad(++urutanbr,3)
            
    }
    function tambahSemua(){
        if(adaygkosong){
            bodynotif = "mohon lengkapi data formulir"
            triggernotif = "show"
            responsestatus = "gagal"
        }else{
            if(berhasilGetId){
                 let sblmnyaIsDafpel = false
                let idDafpel
                document.querySelectorAll("div.dataGroup").forEach(dt =>{
              
                    generateNewPGID(idSebelumnya)
                
    
                
                dt.querySelectorAll("form").forEach(dtf =>{
                    let obj = {};
    
                    //ambil semua data dari form
                    dtf.querySelectorAll("input").forEach(ele => obj[ele.name] = ele.value || "");
                    dtf.querySelectorAll("textarea").forEach(ele => obj[ele.name] = ele.value || "");
                    dtf.querySelectorAll("option").forEach(ele => obj['teknisi'] = ele.value || "");
                    
                    //cek jenis data pelanggan dan kondisi apakah pakai data plg form sebelumnya atau baru
                    if(dt.classList.contains('daftarpel')){
                        if(dtf.name == "datamesin"){
                            obj["id"] = idDafpel
                        }else{
                            obj["id"] = dtf.querySelector('input[name="dafpel"]').value
                            idDafpel = dtf.querySelector('input[name="dafpel"]').value
                            console.log(dt.id + " urutan sblmnya " + urutanbr)
                            if(dt.id !=  "form_0"){
                                urutanbr--
                            }
                            console.log(dt.id + " urutan baru " + urutanbr)
                        }
                        sblmnyaIsDafpel = true
    
                    }else if(dt.classList.contains('sama')){
                        if(sblmnyaIsDafpel){
                            obj["id"] = idDafpel //ambil id dari daftar pelanggan
                        }else{
                            obj["id"] = idSebelumnya //ambil id sebelumnya karna pemilik sama
                        }
                    }else if(dt.classList.contains('beda')){
                        if(sblmnyaIsDafpel || dtf.name == "datamesin"){
                            obj["id"] = idSebelumnya //ambil id sebelumnya karna pemilik sama & untuk data mesin selalu pakai id plg sebelumnya
                        }else{
                            obj["id"] = idBaru //bikin id baru karna pemilik beda
                        }
                        sblmnyaIsDafpel = false
                      
                    }
    
                    //cek jenis data untuk menentukan masuk ke objek yg mana
                    if (dtf.name == "datapemilik") {
                        console.log("data pemilik");    
                        dataPemilik.push(obj)
                    }else if(dtf.name == "datamesin"){
                        console.log("data mesin");
                        dataMesin.push(obj)
                    }
    
                    console.log(obj);
                    profile.push(obj)
                })
                
                    profile.length=0   
                })
    
    
                X.ajax({
                    url:apiurl+"tambahmesin",
                    type:"POST",
                    data: {
                        dataPmk:dataPemilik,
                        dataMsn:dataMesin
                    },
                    
                    beforeSend: function (request) {
                                    request.setRequestHeader('Authorization', getCookie('token'));
                                },
                    success: function(result,status){
                        if(status == "success")
                            {
                                bodynotif = "berhasil menambahkan " + formCount + " data mesin"
                                triggernotif = "show"
                                responsestatus = "sukses"
                                cleanUpForm();
                                getIdPemilik(true);
                            }
    
                    }
                                
                    },
                )
            }else{
                triggernotif = "show"
                    bodynotif = "mohon tunggu sedang mencoba koneksi dengan server"
                    headnotif = "tambah mesin"
            }
        }
       
    }

    function cleanUpForm(){
        formCount = 0
        document.querySelectorAll("div.dataGroup").forEach(dt =>{
            dt.querySelectorAll("form").forEach(dtf =>{
                dataPemilik.length = 0
                dataMesin.length = 0
                dtf.querySelectorAll("input").forEach(ele =>  ele.value = "" );
                dtf.querySelectorAll("textarea").forEach(ele => ele.value = "");
                dtf.querySelectorAll("option").forEach(ele => ele.value = "" );
            })
        })

        setTimeout(function (){
                               ++formCount    
                            }, 200);
                
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

</script>

<div style="display: block; width: 100%; padding-left: 35px">

    <Topbar icon="mesin_tambah" namaHalaman="Tambah Mesin" marginBottom="40"/>
    <div style="display: flex; gap:20px; width: 100%;">
        {#each Array(formCount) as _, id}
        <FormDataMesin bind:formCount formID={id}  buttonTambah={() => tambahSemua()} bind:adaygkosong={adaygkosong}/>
      
        {/each}
       <div style="width:365px; display: flex; align-items:center;justify-content: center; flex-direction:column">
        <img  on:click={addForms} src="./icons/plus_button_big.png" alt="tambah" />
        {#if formCount >1}
        <ButtonSmall  style="margin-top: 10px;" on:click={tambahSemua}>Tambahkan Semua</ButtonSmall>
        {/if}
       </div>
       
    </div>
</div>

{#if triggernotif == "show"}
<NotificationChanges bind:trigger={triggernotif} body={bodynotif} head={headnotif} status={responsestatus}/>
{/if}