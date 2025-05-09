//Aqui nós damos o controle para o admin.

package br.com.eliel.gestao_vagas.modules.admin.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.eliel.gestao_vagas.modules.candidate.entites.CandidateEntity;
import br.com.eliel.gestao_vagas.modules.candidate.repositories.CandidateRepository;
import br.com.eliel.gestao_vagas.modules.company.entites.CompanyEntity;
import br.com.eliel.gestao_vagas.modules.company.repositories.CompanyRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/admin")
@Tag(name = "Administrador", description = "Gerenciamento de usuários e empresas")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    @Autowired
    private CandidateRepository candidateRepository;
    
    @Autowired
    private CompanyRepository companyRepository;
    
    @GetMapping("/candidates")
    @Operation(
        summary = "Listagem de candidatos", 
        description = "Essa função é responsável por listar todos os candidatos",
        security = { @SecurityRequirement(name = "Bearer Authentication") }
    )
    public ResponseEntity<List<CandidateEntity>> getAllCandidates() {
        var candidates = this.candidateRepository.findAll();
        return ResponseEntity.ok().body(candidates);
    }
    
    @GetMapping("/companies")
    @Operation(
        summary = "Listagem de empresas", 
        description = "Essa função é responsável por listar todas as empresas",
        security = { @SecurityRequirement(name = "Bearer Authentication") }
    )
    public ResponseEntity<List<CompanyEntity>> getAllCompanies() {
        var companies = this.companyRepository.findAll();
        return ResponseEntity.ok().body(companies);
    }
    
    @DeleteMapping("/candidates/{id}")
    @Operation(
        summary = "Exclusão de candidato", 
        description = "Essa função é responsável por excluir permanentemente um candidato",
        security = { @SecurityRequirement(name = "Bearer Authentication") }
    )
    public ResponseEntity<Object> deleteCandidate(@PathVariable UUID id) {
        try {
            var candidate = this.candidateRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Candidato não encontrado"));
                
            this.candidateRepository.delete(candidate);
            
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @DeleteMapping("/companies/{id}")
    @Operation(
        summary = "Exclusão de empresa", 
        description = "Essa função é responsável por excluir permanentemente uma empresa",
        security = { @SecurityRequirement(name = "Bearer Authentication") }
    )
    public ResponseEntity<Object> deleteCompany(@PathVariable UUID id) {
        try {
            var company = this.companyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Empresa não encontrada"));
                
            this.companyRepository.delete(company);
            
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }   
}
