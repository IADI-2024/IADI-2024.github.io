package pt.unl.fct.di.demojpa.config.security

import org.springframework.security.access.prepost.PreAuthorize

@PreAuthorize("hasRole('USER')")
annotation class CanReadAllBooks

@PreAuthorize("hasRole('USER')")
annotation class CanAddBooks

@PreAuthorize("hasRole('USER') and @securityService.canReadOneBook(principal,#id)")
annotation class CanReadOneBook