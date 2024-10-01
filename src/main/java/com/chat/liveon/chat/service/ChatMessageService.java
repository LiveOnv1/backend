package com.chat.liveon.chat.service;

import com.chat.liveon.chat.dto.request.ChatMessageRequest;
import com.chat.liveon.chat.entity.ChatMessage;
import com.chat.liveon.chat.repository.ChatMessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;

}
